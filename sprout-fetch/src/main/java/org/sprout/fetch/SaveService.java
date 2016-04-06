/**
 * Created on 2016/2/24
 */
package org.sprout.fetch;

import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import org.sprout.SproutLib;
import org.sprout.cache.CacheService;
import org.sprout.cache.base.CacheHandle;
import org.sprout.cache.conf.StoreConfig;
import org.sprout.core.assist.AppUtils;
import org.sprout.core.assist.StringUtils;
import org.sprout.core.logging.Lc;
import org.sprout.fetch.base.FetchPromiser;
import org.sprout.fetch.save.DroidSaveService;
import org.sprout.fetch.save.SaveException;
import org.sprout.fetch.save.SaveListener;
import org.sprout.fetch.save.SaveObserver;
import org.sprout.fetch.save.SaveProperty;
import org.sprout.fetch.save.SaveRecorder;
import org.sprout.fetch.spec.FetchError;
import org.sprout.fetch.spec.FetchPrior;

import java.io.IOException;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * 下载服务类
 * <p/>
 *
 * @author Cuzki
 */
public final class SaveService {

    private final static String SAVE_ZONE = "task-save";

    // 下载标识
    private final String saveId;
    // 下载地址
    private final String saveUrl;

    // 重试次数
    private int saveRetry;
    // 超时时长
    private int saveTimeout;
    // 下载优先级
    private int savePriority = FetchPrior.NORM.getValue();

    /**
     * 构造下载服务
     * <p/>
     *
     * @param saveId  下载标识
     * @param saveUrl 下载地址
     * @author Cuzki
     */
    SaveService(final String saveId, final String saveUrl) {
        this.saveId = saveId;
        this.saveUrl = saveUrl;
    }

    /**
     * 设置下载优先级
     * <p/>
     *
     * @param savePriority 下载优先级
     * @return 下载服务实例
     * @author Cuzki
     */
    public SaveService prior(final FetchPrior savePriority) {
        if (savePriority != null) {
            this.savePriority = savePriority.getValue();
        }
        return this;
    }

    /**
     * 设置下载重试次数
     *
     * @param saveRetry 重试次数
     * @return 下载服务实例
     * @author Wythe
     */
    public SaveService retry(final int saveRetry) {
        if (saveRetry >= 0) {
            this.saveRetry = saveRetry;
        }
        return this;
    }

    /**
     * 设置下载超时时长
     *
     * @param saveTimeout 超时时长
     * @return 下载服务实例
     * @author Wythe
     */
    public SaveService timeout(final int saveTimeout) {
        if (saveTimeout >= 0) {
            this.saveTimeout = saveTimeout;
        }
        return this;
    }

    /**
     * 开始下载
     * <p/>
     *
     * @param savePath 保存地址
     * @return 下载服务期望
     * @author Cuzki
     */
    public Promiser to(final String savePath) {
        return Promiser.create(saveId, saveUrl, savePath, saveRetry, savePriority, saveTimeout).resume();
    }

    /**
     * 下载服务控制器
     * <p/>
     *
     * @author Wythe
     */
    enum Operator {

        Instance;

        private CacheHandle<Map> mCacheHandle;

        private ServiceConnection mServiceConnect;

        private void initService() {
            final Context context = SproutLib.getContext();
            if (context != null) {
                if (this.mServiceConnect == null) {
                    this.mServiceConnect = new ServiceConnection() {
                        @Override
                        public void onServiceConnected(final ComponentName name, final IBinder service) {
                            if (Lc.D) {
                                Lc.t(SproutLib.name).d("Sprout-fetch save service is connected.");
                            }
                        }

                        @Override
                        public void onServiceDisconnected(final ComponentName name) {
                            if (Lc.D) {
                                Lc.t(SproutLib.name).d("Sprout-fetch save service is disconnected.");
                            }
                        }
                    };
                }
                context.bindService(new Intent(context, DroidSaveService.class), this.mServiceConnect, ContextWrapper.BIND_AUTO_CREATE);
            }
        }

        private void safeExecute(final Action0 action0) {
            Observable.create(new Observable.OnSubscribe<Boolean>() {
                @Override
                public void call(final Subscriber<? super Boolean> subscriber) {
                    if (!AppUtils.isServiceRunning(DroidSaveService.class)) {
                        initService();
                    }
                    subscriber.onCompleted();
                }
            }).doOnCompleted(action0).subscribe();
        }

        /**
         * 销毁控制器
         *
         * @author Wythe
         */
        void destroy() {
            if (this.mServiceConnect != null) {
                final Context context = SproutLib.getContext();
                if (AppUtils.isServiceRunning(DroidSaveService.class)) {
                    final Intent intent = new Intent(context, DroidSaveService.class);
                    try {
                        context.stopService(intent);
                    } catch (Exception e) {
                        if (Lc.E) {
                            Lc.t(SproutLib.name).e(e);
                        }
                    } finally {
                        try {
                            context.unbindService(this.mServiceConnect);
                        } catch (Exception e) {
                            if (Lc.E) {
                                Lc.t(SproutLib.name).e(e);
                            }
                        } finally {
                            this.mCacheHandle = null;
                            this.mServiceConnect = null;
                        }
                    }
                }
            }
            if (this.mCacheHandle != null) {
                if (this.mCacheHandle.alive()) {
                    try {
                        this.mCacheHandle.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        this.mCacheHandle.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                this.mCacheHandle = null;
            }
        }

        private void startDownload(final String saveId, final Promiser saveAttr) {
            if (!StringUtils.isEmpty(saveId) && !StringUtils.isEmpty(saveAttr.saveUrl) && !StringUtils.isEmpty(saveAttr.savePath)) {
                final Context context = SproutLib.getContext();
                if (context != null) {
                    this.safeExecute(new Action0() {
                        @Override
                        public void call() {
                            final Intent intent = new Intent(context, DroidSaveService.class);
                            intent.putExtra(DroidSaveService.INTENT_COMMAND_KEY, DroidSaveService.COMMAND_START);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_SPACE_KEY, SAVE_ZONE);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_ID_KEY, saveId);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_URL_KEY, saveAttr.saveUrl);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_PATH_KEY, saveAttr.savePath);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_RETRY_KEY, saveAttr.saveRetry);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_PRIOR_KEY, saveAttr.savePrior);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_TIMEOUT_KEY, saveAttr.saveTimeout);
                            context.startService(intent);
                        }
                    });
                }
            }
        }

        private void pauseDownload(final String saveId) {
            if (!StringUtils.isEmpty(saveId)) {
                final Context context = SproutLib.getContext();
                if (context != null) {
                    this.safeExecute(new Action0() {
                        @Override
                        public void call() {
                            final Intent intent = new Intent(context, DroidSaveService.class);
                            intent.putExtra(DroidSaveService.INTENT_COMMAND_KEY, DroidSaveService.COMMAND_PAUSE);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_SPACE_KEY, SAVE_ZONE);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_ID_KEY, saveId);
                            context.startService(intent);
                        }
                    });
                }
            }
        }

        private void cancelDownload(final String saveId) {
            if (!StringUtils.isEmpty(saveId)) {
                final Context context = SproutLib.getContext();
                if (context != null) {
                    this.safeExecute(new Action0() {
                        @Override
                        public void call() {
                            final Intent intent = new Intent(context, DroidSaveService.class);
                            intent.putExtra(DroidSaveService.INTENT_COMMAND_KEY, DroidSaveService.COMMAND_CANCEL);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_SPACE_KEY, SAVE_ZONE);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_ID_KEY, saveId);
                            context.startService(intent);
                        }
                    });
                }
            }
        }

        private void registListener(final String saveId, final SaveListener saveListener) {
            if (!StringUtils.isEmpty(saveId) && saveListener != null) {
                final Context context = SproutLib.getContext();
                if (context != null) {
                    SaveObserver.registListener(saveId, saveListener);
                    // 校正监听
                    this.safeExecute(new Action0() {
                        @Override
                        public void call() {
                            final Intent intent = new Intent(context, DroidSaveService.class);
                            intent.putExtra(DroidSaveService.INTENT_COMMAND_KEY, DroidSaveService.COMMAND_WATCH);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_SPACE_KEY, SAVE_ZONE);
                            intent.putExtra(DroidSaveService.INTENT_SAVE_ID_KEY, saveId);
                            context.startService(intent);
                        }
                    });
                }
            }
        }

        private void removeListener(final String saveId, final SaveListener saveListener) {
            if (!StringUtils.isEmpty(saveId) && saveListener != null) {
                SaveObserver.removeListener(saveId, saveListener);
            }
        }

        @SuppressWarnings("unchecked")
        private SaveProperty selectRecorder(final String saveId) {
            if (StringUtils.isEmpty(saveId)) {
                return null;
            }
            if (this.mCacheHandle == null || !this.mCacheHandle.alive()) {
                try {
                    this.mCacheHandle = CacheService.store(Map.class, StoreConfig.apply(FetchService.getRecordPath() + SAVE_ZONE).setSize(Integer.MAX_VALUE).build());
                } catch (Exception e) {
                    return null;
                }
            }
            return this.mCacheHandle != null ? SaveRecorder.decapsulate(this.mCacheHandle.get(saveId)) : null;
        }

    }

    /**
     * 下载服务期望类
     * <p/>
     *
     * @author Wythe
     */
    public final static class Promiser extends FetchPromiser<Promiser, SaveProperty, SaveListener> {

        private boolean hopeApprove;

        private final int saveRetry;

        private final int savePrior;

        private final int saveTimeout;

        private final String saveUrl;

        private final String savePath;

        /**
         * 下载服务期望构造函数
         *
         * @param saveId      下载标识
         * @param saveUrl     下载地址
         * @param savePath    保存地址
         * @param saveRetry   下载重试
         * @param savePrior   下载优先级
         * @param saveTimeout 下载超时时长
         * @author Wythe
         */
        private Promiser(final String saveId, final String saveUrl, final String savePath, final int saveRetry, final int savePrior, final int saveTimeout) {
            super(saveId);
            this.saveUrl = saveUrl;
            this.savePath = savePath;
            this.saveRetry = saveRetry;
            this.savePrior = savePrior;
            this.saveTimeout = saveTimeout;
            this.hopeApprove = !StringUtils.isEmpty(this.taskId) && !StringUtils.isEmpty(this.saveUrl) && !StringUtils.isEmpty(this.savePath);
        }

        static Promiser create(final String saveId) {
            if (!StringUtils.isEmpty(saveId)) {
                final SaveProperty property = Operator.Instance.selectRecorder(saveId);
                if (property != null) {
                    return Promiser.create(property.getTaskId(), property.getSaveUrl(), property.getSavePath(), property.getSaveRetry(), property.getSavePrior().getValue(), property.getSaveTimeout());
                }
            }
            return null;
        }

        private static Promiser create(final String saveId, final String saveUrl, final String savePath, final int saveRetry, final int savePrior, final int saveTimeout) {
            return new Promiser(saveId, saveUrl, savePath, saveRetry, savePrior, saveTimeout);
        }

        /**
         * 获得信息
         *
         * @return 下载属性
         * @author Wythe
         */
        @Override
        public SaveProperty info() {
            return this.hopeApprove ? Operator.Instance.selectRecorder(this.taskId) : null;
        }

        /**
         * 监听下载
         *
         * @param listener 监听器
         * @return 下载期望
         * @author Wythe
         */
        @Override
        public Promiser then(final SaveListener listener) {
            if (listener != null) {
                if (this.hopeApprove) {
                    Operator.Instance.registListener(this.taskId, listener);
                } else {
                    listener.onError(new SaveException(this.taskId, FetchError.PARAM_ERR.getCode(), FetchError.PARAM_ERR.getMessage()));
                }
            }
            return this;
        }

        /**
         * 暂停下载
         *
         * @return 下载期望
         * @author Wythe
         */
        public Promiser pause() {
            if (this.hopeApprove) {
                Operator.Instance.pauseDownload(this.taskId);
            }
            return this;
        }

        /**
         * 取消下载
         *
         * @return 下载期望
         * @author Wythe
         */
        public Promiser cancel() {
            if (this.hopeApprove) {
                this.hopeApprove = false;
                // 清除下载
                Operator.Instance.cancelDownload(this.taskId);
            }
            return this;
        }

        /**
         * 恢复下载
         *
         * @return 下载期望
         * @author Wythe
         */
        public Promiser resume() {
            if (this.hopeApprove) {
                Operator.Instance.startDownload(this.taskId, this);
            }
            return this;
        }

        /**
         * 屏蔽监听
         *
         * @param listener 监听器
         * @return 下载期望
         * @author Wythe
         */
        public Promiser shield(final SaveListener listener) {
            if (this.hopeApprove && listener != null) {
                Operator.Instance.removeListener(this.taskId, listener);
            }
            return this;
        }

    }

}
