/**
 * Created on 2016/2/5
 */
package org.sprout.fetch.save;

import android.app.Service;
import android.content.Intent;
import android.os.HandlerThread;
import android.os.IBinder;

import org.sprout.SproutLib;
import org.sprout.core.assist.StringUtils;
import org.sprout.core.assist.TimerUtils;
import org.sprout.core.logging.Lc;
import org.sprout.fetch.FetchService;
import org.sprout.fetch.spec.FetchError;
import org.sprout.fetch.spec.FetchPrior;
import org.sprout.fetch.spec.FetchStatus;

import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import rx.Observable;
import rx.functions.Action0;

/**
 * 下载系统服务类
 * <p/>
 *
 * @author Cuzki
 */
public final class DroidSaveService extends Service {

    private final static long SAVE_POLL_TIME = 1000L;

    private final static String SAVE_POLL_CTRL = "_$SAVE_POLL_CTRL";

    // 开始指令
    public final static int COMMAND_START = 1;
    // 暂停指令
    public final static int COMMAND_PAUSE = 2;
    // 监听指令
    public final static int COMMAND_WATCH = 3;
    // 取消指令
    public final static int COMMAND_CANCEL = 4;

    public final static String INTENT_COMMAND_KEY = "_$BUNDLE_COMMAND";
    public final static String INTENT_SAVE_ID_KEY = "_$BUNDLE_SAVE_ID";
    public final static String INTENT_SAVE_URL_KEY = "_$BUNDLE_SAVE_URL";
    public final static String INTENT_SAVE_PATH_KEY = "_$BUNDLE_SAVE_PATH";
    public final static String INTENT_SAVE_SPACE_KEY = "_$BUNDLE_SAVE_SPACE";
    public final static String INTENT_SAVE_RETRY_KEY = "_$BUNDLE_SAVE_RETRY";
    public final static String INTENT_SAVE_PRIOR_KEY = "_$BUNDLE_SAVE_PRIOR";
    public final static String INTENT_SAVE_TIMEOUT_KEY = "_$BUNDLE_SAVE_TIMEOUT";

    private SaveRecorder mSaveRecorder;

    private final Deque<SaveScheduler> mSchedulerList = new LinkedList<>();

    private final Map<String, SaveSubscription> mSubscriptionMap = new HashMap<>();

    @Override
    public int onStartCommand(final Intent intent, final int flags, final int startId) {
        if (intent != null) {
            final int command = intent.getIntExtra(INTENT_COMMAND_KEY, 0);
            if (command > 0) {
                final String saveId = intent.getStringExtra(INTENT_SAVE_ID_KEY), saveSpace = intent.getStringExtra(INTENT_SAVE_SPACE_KEY);
                if (!StringUtils.isEmpty(saveId) && !StringUtils.isEmpty(saveSpace)) {
                    // 初始缓存
                    if (this.mSaveRecorder == null || this.mSaveRecorder.isShut()) {
                        try {
                            this.mSaveRecorder = new SaveRecorder(saveSpace);
                        } catch (Exception e) {
                            this.mSaveRecorder = null;
                            if (Lc.E) {
                                Lc.t(SproutLib.name).e("FetchService save record init error.", e);
                            }
                        }
                    }
                    // 校验缓存
                    if (this.mSaveRecorder == null || this.mSaveRecorder.isShut()) {
                        // 缓存异常
                        SaveExecutor.reportError(saveId, FetchError.RECORD_ERR);
                    } else {
                        if (COMMAND_START != command) {
                            switch (command) {
                                // 监听下载
                                case COMMAND_WATCH: {
                                    synchronized (DroidSaveService.this) {
                                        this.watchSave(saveId);
                                    }
                                    break;
                                }
                                // 暂停下载
                                case COMMAND_PAUSE: {
                                    synchronized (DroidSaveService.this) {
                                        this.pauseSave(saveId);
                                    }
                                    break;
                                }
                                // 取消下载
                                case COMMAND_CANCEL: {
                                    synchronized (DroidSaveService.this) {
                                        this.clearSave(saveId);
                                    }
                                    break;
                                }
                            }
                        } else {
                            // 启动下载
                            final String fileUrl = intent.getStringExtra(INTENT_SAVE_URL_KEY);
                            final String savePath = intent.getStringExtra(INTENT_SAVE_PATH_KEY);
                            if (!StringUtils.isEmpty(fileUrl) && !StringUtils.isEmpty(savePath)) {
                                final SaveScheduler saveScheduler = new SaveScheduler(saveId, fileUrl, savePath, intent.getIntExtra(INTENT_SAVE_RETRY_KEY, 0), intent.getIntExtra(INTENT_SAVE_PRIOR_KEY, 0), intent.getIntExtra(INTENT_SAVE_TIMEOUT_KEY, 0));
                                synchronized (DroidSaveService.this) {
                                    this.startSave(saveScheduler);
                                }
                            }
                        }
                    }
                }
            }
        }
        return START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(final Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (this.mSaveTimer != null) {
            this.mSaveTimer.stop();
        }
        try {
            synchronized (DroidSaveService.this) {
                this.mSchedulerList.clear();
                this.stopSaveSubscription();
                if (this.mSaveRecorder != null) {
                    if (!this.mSaveRecorder.isShut()) {
                        try {
                            this.mSaveRecorder.mCacheHandle.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        try {
                            this.mSaveRecorder.mCacheHandle.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    this.mSaveRecorder = null;
                }
            }
            if (Lc.D) {
                Lc.t(SproutLib.name).d("FetchService save free");
            }
        } catch (Exception e) {
            if (Lc.E) {
                Lc.t(SproutLib.name).e("FetchService save free error.", e);
            }
        } finally {
            super.onDestroy();
        }
    }

    private void clearSave(final String saveId) {
        this.removeSaveScheduler(saveId);
        this.stopSaveSubscription(saveId);
        SaveObserver.mListenerHash.remove(saveId);
        SaveExecutor.destroyDownLoad(saveId, this.mSaveRecorder);
        if (Lc.D) {
            Lc.t(SproutLib.name).d("FetchService save abort: " + saveId);
        }
    }

    private void watchSave(final String saveId) {
        // 等待下载
        if (this.searchSaveScheduler(saveId) != null) {
            return;
        }
        // 正在下载
        if (this.mSubscriptionMap.containsKey(saveId)) {
            return;
        }
        // 暂停下载
        final SaveProperty property = this.mSaveRecorder.selectRecorder(saveId);
        if (property != null) {
            final FetchStatus status = property.getSaveStatus();
            if (FetchStatus.PAUSE.equals(status)) {
                return;
            }
            if (FetchStatus.START.equals(status) || FetchStatus.AWAIT.equals(status)) {
                SaveExecutor.reportPause(this.mSaveRecorder.updateSaveStatus(property, FetchStatus.PAUSE));
                return;
            }
        }
        SaveObserver.mListenerHash.remove(saveId);
    }

    private void pauseSave(final String saveId) {
        final SaveScheduler saveScheduler = this.searchSaveScheduler(saveId);
        if (saveScheduler != null) {
            this.mSchedulerList.remove(saveScheduler);
        }
        if (this.mSubscriptionMap.containsKey(saveId)) {
            this.stopSaveSubscription(saveId);
        } else {
            if (saveScheduler != null) {
                final SaveProperty property = this.mSaveRecorder.selectRecorder(saveId);
                if (property != null) {
                    final FetchStatus status = property.getSaveStatus();
                    if (FetchStatus.START.equals(status) || FetchStatus.AWAIT.equals(status)) {
                        SaveExecutor.reportPause(this.mSaveRecorder.updateSaveStatus(property, FetchStatus.PAUSE));
                    }
                    return;
                }
                if (Lc.D) {
                    Lc.t(SproutLib.name).d("FetchService save pause: " + saveId);
                }
            }
        }
    }

    private void startSave(final SaveScheduler saveScheduler) {
        // 查询记录
        SaveProperty property = this.mSaveRecorder.selectRecorder(saveScheduler.saveId);
        if (property == null) {
            // 异常任务
            if (this.mSubscriptionMap.containsKey(saveScheduler.saveId)) {
                this.stopSaveSubscription(saveScheduler.saveId);
            }
        } else {
            if (!saveScheduler.savePath.equals(property.getSavePath())) {
                // 任务冲突
                SaveExecutor.reportError(saveScheduler.saveId, FetchError.SAVETASK_CONFLICT_ERR);
                return;
            }
            // 正在执行
            if (this.mSubscriptionMap.containsKey(saveScheduler.saveId)) {
                return;
            }
            // 状态校验
            if (FetchStatus.FINISH.equals(property.getSaveStatus())) {
                final File saveFile = new File(saveScheduler.savePath);
                if (saveFile.exists()) {
                    if (property.getFileSize() != property.getSaveSize() || property.getSaveSize() != saveFile.length()) {
                        // 下载异常
                        SaveExecutor.reportError(
                                this.mSaveRecorder.updateSaveStatus(property, FetchStatus.ERROR).getTaskId(), FetchError.SAVEFILE_DATA_ERR
                        );
                    } else {
                        // 下载完成
                        SaveExecutor.reportFinish(property);
                    }
                    return;
                }
                // 更新大小
                final File tempFile = new File(SaveExecutor.getTempFilePath(saveScheduler.savePath));
                if (tempFile.exists()) {
                    tempFile.delete();
                }
                // 属性还原
                property = this.mSaveRecorder.revertScheduler(property, saveScheduler);
            }
        }
        // 判断队列
        SaveScheduler takeScheduler = this.searchSaveScheduler(saveScheduler.saveId);
        if (takeScheduler != null) {
            if (FetchPrior.NOW.getValue() != saveScheduler.savePrior) {
                // 正在等待
                takeScheduler.saveUrl = saveScheduler.saveUrl;
                takeScheduler.saveRetry = saveScheduler.saveRetry;
                takeScheduler.savePrior = saveScheduler.savePrior;
                takeScheduler.saveTimeout = saveScheduler.saveTimeout;
                // 等待通知
                SaveExecutor.reportAwait(this.mSaveRecorder.updateScheduler(property, saveScheduler));
                return;
            }
            this.mSchedulerList.remove(takeScheduler);
        } else {
            if (FetchPrior.NOW.getValue() != saveScheduler.savePrior) {
                if (FetchService.getSaveQueue() <= this.mSchedulerList.size()) {
                    // 队列异常
                    if (property != null) {
                        this.mSaveRecorder.updateSaveStatus(property, FetchStatus.ERROR);
                    }
                    SaveExecutor.reportError(saveScheduler.saveId, FetchError.QUEUE_ERR);
                } else {
                    // 等待下载
                    if (FetchPrior.HIGH.getValue() == saveScheduler.savePrior) {
                        this.mSchedulerList.addLast(saveScheduler);
                    } else {
                        this.mSchedulerList.addFirst(saveScheduler);
                    }
                    SaveExecutor.reportAwait(this.mSaveRecorder.updateScheduler(property, saveScheduler));
                }
                return;
            }
        }
        // 判断线程
        takeScheduler = null;
        if (FetchService.getSaveThread() <= this.mSubscriptionMap.size()) {
            SaveSubscription execSubscription = null;
            for (final SaveSubscription value : this.mSubscriptionMap.values()) {
                if (value != null && !FetchPrior.NOW.equals(value.getSavePrior())) {
                    takeScheduler = (execSubscription = value).getSaveScheduler();
                }
            }
            if (takeScheduler == null) {
                takeScheduler = saveScheduler;
            } else {
                this.stopSaveSubscription(execSubscription);
            }
            // 等待下载
            this.mSchedulerList.addLast(takeScheduler);
            if (FetchService.getSaveQueue() < this.mSchedulerList.size()) {
                final SaveScheduler wipeScheduler = mSchedulerList.removeFirst();
                if (wipeScheduler != null) {
                    // 队列异常
                    this.mSaveRecorder.updateSaveStatus(
                            wipeScheduler.saveId, FetchStatus.ERROR
                    );
                    SaveExecutor.reportError(wipeScheduler.saveId, FetchError.QUEUE_ERR);
                }
            }
            SaveExecutor.reportAwait(this.mSaveRecorder.updateScheduler(property, takeScheduler));
        }
        // 启动下载
        if (saveScheduler != takeScheduler) {
            this.registSaveSubscription(this.mSaveRecorder, saveScheduler);
        }
    }

    private SaveScheduler searchSaveScheduler(final String saveId) {
        if (this.mSchedulerList.size() > 0) {
            for (final SaveScheduler saveScheduler : this.mSchedulerList) {
                if (saveId.equals(saveScheduler.saveId)) {
                    return saveScheduler;
                }
            }
        }
        return null;
    }

    private void removeSaveScheduler(final String saveId) {
        if (this.mSchedulerList.size() > 0) {
            for (final SaveScheduler saveScheduler : this.mSchedulerList) {
                if (saveId.equals(saveScheduler.saveId)) {
                    this.mSchedulerList.remove(saveScheduler);
                    break;
                }
            }
        }
    }

    private void stopSaveSubscription() {
        if (this.mSubscriptionMap.size() > 0) {
            for (final SaveSubscription saveSubscription : this.mSubscriptionMap.values()) {
                this.stopSaveSubscription(saveSubscription);
            }
        }
    }

    private void stopSaveSubscription(final String saveId) {
        if (this.mSubscriptionMap.containsKey(saveId)) {
            this.stopSaveSubscription(this.mSubscriptionMap.get(saveId));
        }
    }

    private void stopSaveSubscription(final SaveSubscription saveSubscription) {
        if (saveSubscription != null) {
            if (!saveSubscription.isUnsubscribed()) {
                saveSubscription.unsubscribe();
            } else {
                this.mSubscriptionMap.remove(saveSubscription.getSaveId());
            }
        }
    }

    private void registSaveSubscription(final SaveRecorder saveRecorder, final SaveScheduler saveScheduler) {
        final SaveSubscription saveSubscription = this.mSubscriptionMap.get(saveScheduler.saveId);
        if (saveSubscription == null || saveSubscription.isUnsubscribed()) {
            final Observable<SaveProperty> observable = SaveExecutor.observableDownLoad(saveRecorder, saveScheduler, new Action0() {
                @Override
                public void call() {
                    synchronized (DroidSaveService.this) {
                        mSubscriptionMap.remove(saveScheduler.saveId);
                    }
                    if (Lc.D) {
                        Lc.t(SproutLib.name).d("FetchService save close: " + saveScheduler.saveId);
                    }
                }
            });
            if (observable == null) {
                this.mSubscriptionMap.remove(saveScheduler.saveId);
            } else {
                this.mSubscriptionMap.put(saveScheduler.saveId, new SaveSubscription(
                        observable.subscribe(new SaveSubscriber(saveScheduler.saveId)), saveScheduler
                ));
            }
        }
    }

    private final TimerUtils mSaveTimer = TimerUtils.repet(SAVE_POLL_TIME, new HandlerThread(SAVE_POLL_CTRL), new TimerUtils.Listener() {
        @Override
        public void onTime() {
            if (mSaveRecorder != null && !mSaveRecorder.isShut()) {
                synchronized (DroidSaveService.this) {
                    if (FetchService.getSaveThread() > mSubscriptionMap.size() && mSchedulerList.size() > 0) {
                        final SaveScheduler saveScheduler = mSchedulerList.removeLast();
                        if (saveScheduler != null) {
                            registSaveSubscription(mSaveRecorder, saveScheduler);
                        }
                    }
                }
            }
        }
    });

}
