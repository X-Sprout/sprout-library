/**
 * Created on 2016/2/16
 */
package org.sprout.fetch.save;

import org.sprout.SproutLib;
import org.sprout.bale.reactivex.schedulers.AndroidSchedulers;
import org.sprout.core.assist.CollectionUtils;
import org.sprout.core.assist.ErrorUtils;
import org.sprout.core.assist.NetUtils;
import org.sprout.core.assist.StringUtils;
import org.sprout.core.logging.Lc;
import org.sprout.fetch.spec.FetchError;
import org.sprout.fetch.spec.FetchStatus;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.RandomAccessFile;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 下载服务执行类
 * <p/>
 *
 * @author Cuzki
 */
final class SaveExecutor {

    // 读取块区大小
    private final static int SIZE_BUFFER = 4096;
    // 下载超时时长
    private final static int SAVE_TIMEOUT = 15000;
    // 下载超时时长
    private final static long SAVE_DORMANT = 5000;
    // 文件输出模式
    private final static String MODE_OUTPUT = "rw";
    // 临时文件后缀
    private final static String TEMP_SUFFIX = ".fstmp";
    // 网络请求客户端
    private final static OkHttpClient HTTP_CLIENT = (new OkHttpClient.Builder()).build();

    /**
     * 销毁下载内容
     *
     * @param saveId   下载标识
     * @param recorder 下载记录
     * @author Cuzki
     */
    static void destroyDownLoad(final String saveId, final SaveRecorder recorder) {
        if (!StringUtils.isEmpty(saveId) && recorder != null) {
            final SaveProperty property = recorder.selectRecorder(saveId);
            if (property != null) {
                final String savePath = property.getSavePath();
                if (!StringUtils.isEmpty(savePath)) {
                    final File saveFile = new File(savePath);
                    if (saveFile.exists()) {
                        saveFile.delete();
                    }
                    final File tempFile = new File(SaveExecutor.getTempFilePath(savePath));
                    if (tempFile.exists()) {
                        tempFile.delete();
                    }
                }
                recorder.removeRecorder(saveId);
            }
        }
    }

    /**
     * 下载错误通知
     *
     * @param saveId    下载标识
     * @param saveError 错误原因
     * @author Wythe
     */
    static void reportError(final String saveId, final FetchError saveError) {
        if (!StringUtils.isEmpty(saveId) && saveError != null) {
            final List<SaveListener> callbackList = SaveObserver.searchListener(saveId);
            if (callbackList != null) {
                SaveObserver.mListenerHash.remove(saveId);
                if (callbackList.size() > 0) {
                    Observable.from(callbackList).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SaveListener>() {
                        @Override
                        public void call(final SaveListener saveListener) {
                            if (saveListener != null) {
                                try {
                                    saveListener.onError(new SaveException(saveId, saveError.getCode(), saveError.getMessage()));
                                } catch (Exception e) {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e("FetchService save error callback exception.", e);
                                    }
                                }
                            }
                        }
                    });
                }
            }
            if (Lc.D) {
                Lc.t(SproutLib.name).d("FetchService save error: " + saveId + " -> " + saveError.getMessage());
            }
        }
    }

    /**
     * 下载等待通知
     *
     * @param property 下载属性
     * @author Wythe
     */
    static void reportAwait(final SaveProperty property) {
        if (property != null) {
            final List<SaveListener> callbackList = SaveObserver.searchListener(property.getTaskId());
            if (!CollectionUtils.isEmpty(callbackList)) {
                Observable.from(callbackList).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SaveListener>() {
                    @Override
                    public void call(final SaveListener saveListener) {
                        if (saveListener != null) {
                            try {
                                saveListener.onAwait(property);
                            } catch (Exception e) {
                                if (Lc.E) {
                                    Lc.t(SproutLib.name).e("FetchService save await callback exception.", e);
                                }
                            }
                        }
                    }
                });
            }
            if (Lc.D) {
                Lc.t(SproutLib.name).d("FetchService save await: " + property.getTaskId());
            }
        }
    }

    /**
     * 下载开始通知
     *
     * @param property 下载属性
     * @author Wythe
     */
    private static void reportStart(final SaveProperty property) {
        if (property != null) {
            final List<SaveListener> callbackList = SaveObserver.searchListener(property.getTaskId());
            if (!CollectionUtils.isEmpty(callbackList)) {
                Observable.from(callbackList).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SaveListener>() {
                    @Override
                    public void call(final SaveListener saveListener) {
                        if (saveListener != null) {
                            try {
                                saveListener.onStart(property);
                            } catch (Exception e) {
                                if (Lc.E) {
                                    Lc.t(SproutLib.name).e("FetchService save start callback exception.", e);
                                }
                            }
                        }
                    }
                });
            }
            if (Lc.D) {
                Lc.t(SproutLib.name).d("FetchService save start: " + property.getTaskId());
            }
        }
    }

    /**
     * 下载暂停通知
     *
     * @param property 下载属性
     * @author Wythe
     */
    static void reportPause(final SaveProperty property) {
        if (property != null) {
            final List<SaveListener> callbackList = SaveObserver.searchListener(property.getTaskId());
            if (!CollectionUtils.isEmpty(callbackList)) {
                Observable.from(callbackList).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SaveListener>() {
                    @Override
                    public void call(final SaveListener saveListener) {
                        if (saveListener != null) {
                            try {
                                saveListener.onPause(property);
                            } catch (Exception e) {
                                if (Lc.E) {
                                    Lc.t(SproutLib.name).e("FetchService save pause callback exception.", e);
                                }
                            }
                        }
                    }
                });
            }
            if (Lc.D) {
                Lc.t(SproutLib.name).d("FetchService save pause: " + property.getTaskId());
            }
        }
    }

    /**
     * 下载完成通知
     *
     * @param property 下载属性
     * @author Wythe
     */
    static void reportFinish(final SaveProperty property) {
        if (property != null) {
            final List<SaveListener> callbackList = SaveObserver.searchListener(property.getTaskId());
            if (callbackList != null) {
                SaveObserver.mListenerHash.remove(property.getTaskId());
                if (callbackList.size() > 0) {
                    Observable.from(callbackList).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<SaveListener>() {
                        @Override
                        public void call(final SaveListener saveListener) {
                            if (saveListener != null) {
                                try {
                                    saveListener.onFinish(property);
                                } catch (Exception e) {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e("FetchService save finish callback exception.", e);
                                    }
                                }
                            }
                        }
                    });
                }
            }
            if (Lc.D) {
                Lc.t(SproutLib.name).d("FetchService save finish: " + property.getTaskId());
            }
        }
    }

    /**
     * 获得下载临时文件路径
     *
     * @param savePath 保存地址
     * @return 临时文件路径
     * @author Cuzki
     */
    static String getTempFilePath(final String savePath) {
        if (StringUtils.isEmpty(savePath)) {
            return StringUtils.EMPTY;
        }
        return savePath + TEMP_SUFFIX;
    }

    /**
     * 执行下载任务
     *
     * @param recorder    记录句柄
     * @param property    任务属性
     * @param unscheduler 完成动作
     * @return 下载观察对象
     * @author Cuzki
     */
    @SuppressWarnings("unchecked")
    static Observable<SaveProperty> observableDownLoad(final SaveRecorder recorder, final SaveProperty property, final Action0 unscheduler) {
        return recorder == null || property == null || unscheduler == null ? null : Observable.create(new Observable.OnSubscribe<SaveProperty>() {
            @Override
            public void call(final Subscriber<? super SaveProperty> subscriber) {
                SaveExecutor.downloadFile(recorder, property, (Subscriber<SaveProperty>) subscriber);
            }
        }).doOnTerminate(unscheduler).doOnUnsubscribe(unscheduler).subscribeOn(Schedulers.io()).onBackpressureLatest().observeOn(AndroidSchedulers.mainThread()).doOnError(new Action1<Throwable>() {
            @Override
            public void call(final Throwable throwable) {
                // 下载异常
                final String saveId = property.getTaskId();
                if (!StringUtils.isEmpty(saveId)) {
                    if (!recorder.isShut() && !FetchStatus.FINISH.equals(property.getSaveStatus())) {
                        recorder.updateSaveStatus(property, FetchStatus.ERROR);
                    }
                    // 异常通知
                    final List<SaveListener> callbackList = SaveObserver.searchListener(saveId);
                    if (callbackList != null) {
                        if (callbackList.size() > 0) {
                            final SaveException report = SaveException.class.isInstance(throwable) ? (SaveException) throwable : new SaveException(
                                    saveId, FetchError.UNKNOWN_ERR.getCode(), FetchError.UNKNOWN_ERR.getMessage(), throwable
                            );
                            for (final SaveListener callback : callbackList) {
                                if (callback != null) {
                                    try {
                                        callback.onError(report);
                                    } catch (Exception e) {
                                        if (Lc.E) {
                                            Lc.t(SproutLib.name).e("FetchService save error callback exception.", e);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (Lc.E) {
                    Lc.t(SproutLib.name).e("FetchService save error: " + saveId + (throwable == null ? "" : " -> " + ErrorUtils.getCause(throwable)));
                }
            }
        }).doOnSubscribe(new Action0() {
            @Override
            public void call() {
                // 下载准备
                final String saveId = property.getTaskId();
                if (!StringUtils.isEmpty(saveId)) {
                    final List<SaveListener> callbackList = SaveObserver.searchListener(saveId);
                    if (!CollectionUtils.isEmpty(callbackList)) {
                        for (final SaveListener saveListener : callbackList) {
                            if (saveListener != null) {
                                try {
                                    saveListener.onReady(saveId);
                                } catch (Exception e) {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e("FetchService save ready callback exception.", e);
                                    }
                                }
                            }
                        }
                    }
                    if (Lc.D) {
                        Lc.t(SproutLib.name).d("FetchService save ready: " + saveId);
                    }
                }
            }
        });
    }

    private static void downloadFile(final SaveRecorder recorder, final SaveProperty property, final Subscriber<SaveProperty> subscriber) {
        final File saveFile = new File(property.getSavePath()), tempFile = new File(getTempFilePath(property.getSavePath()));
        if (property.getSaveSize() > property.getFileSize()) {
            // 已下载文件大小是否异常
            recorder.updateSaveStatus(property, FetchStatus.ERROR);
            try {
                subscriber.onCompleted();
            } catch (Exception e) {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(e);
                }
            } finally {
                SaveExecutor.reportError(property.getTaskId(), FetchError.SAVESIZE_MORE_ERR);
            }
            return;
        }
        if (saveFile.exists()) {
            if (property.getFileSize() > 0 && property.getFileSize() == property.getSaveSize() && property.getFileSize() == saveFile.length()) {
                // 文件已成功下载
                recorder.updateSaveStatus(property, FetchStatus.FINISH);
                try {
                    subscriber.onCompleted();
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    SaveExecutor.reportFinish(property);
                }
            } else {
                // 已下载文件数据不匹配
                recorder.updateSaveStatus(property, FetchStatus.ERROR);
                try {
                    subscriber.onCompleted();
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    SaveExecutor.reportError(property.getTaskId(), FetchError.SAVEFILE_DATA_ERR);
                }
            }
            return;
        }
        if (tempFile.exists()) {
            if (property.getFileSize() > 0) {
                final long tempSize = tempFile.length();
                if (property.getSaveSize() == tempSize) {
                    if (property.getSaveSize() == property.getFileSize()) {
                        // 临时文件已成功下载
                        if (tempFile.renameTo(saveFile)) {
                            recorder.updateSaveStatus(property, FetchStatus.FINISH);
                            try {
                                subscriber.onCompleted();
                            } catch (Exception e) {
                                if (Lc.E) {
                                    Lc.t(SproutLib.name).e(e);
                                }
                            } finally {
                                SaveExecutor.reportFinish(property);
                            }
                        } else {
                            recorder.updateSaveStatus(property, FetchStatus.ERROR);
                            try {
                                subscriber.onCompleted();
                            } catch (Exception e) {
                                if (Lc.E) {
                                    Lc.t(SproutLib.name).e(e);
                                }
                            } finally {
                                SaveExecutor.reportError(property.getTaskId(), FetchError.TEMPFILE_CONVERT_ERR);
                            }
                        }
                        return;
                    }
                } else {
                    // 同步缓存大小
                    recorder.updateSaveSize(property, tempSize);
                }
            } else {
                if (property.getSaveSize() > 0) {
                    recorder.updateSaveStatus(property, FetchStatus.ERROR);
                    try {
                        subscriber.onCompleted();
                    } catch (Exception e) {
                        if (Lc.E) {
                            Lc.t(SproutLib.name).e(e);
                        }
                    } finally {
                        SaveExecutor.reportError(property.getTaskId(), FetchError.FILESIZE_LOST_ERR);
                    }
                    return;
                } else {
                    tempFile.delete();
                }
            }
        } else {
            // 更新缓存大小
            if (property.getSaveSize() > 0) {
                recorder.updateSaveSize(property, 0L);
            }
        }
        // 正式下载
        SaveExecutor.downloadFile(recorder, property, subscriber, property.getSaveRetry(), saveFile, tempFile);
    }

    private static void downloadFile(final SaveRecorder recorder, final SaveProperty property, final Subscriber<SaveProperty> subscriber, final int saveRetry, final File saveFile, final File tempFile) {
        // 网络检测
        if (!NetUtils.isConnected(true)) {
            // 网络异常
            recorder.updateSaveStatus(property, FetchStatus.ERROR);
            try {
                subscriber.onCompleted();
            } catch (Exception e) {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(e);
                }
            } finally {
                SaveExecutor.reportError(property.getTaskId(), FetchError.NETWORK_ERR);
            }
            return;
        }
        // 发起请求
        final Response httpResponse;
        try {
            httpResponse = (HTTP_CLIENT.newBuilder().connectTimeout((long) (property.getSaveTimeout() > 0 ? property.getSaveTimeout() : SAVE_TIMEOUT), TimeUnit.MILLISECONDS).build()).newCall(
                    new Request.Builder().url(property.getSaveUrl()).addHeader("Range", String.format("bytes=%d-", (property.getFileSize() > 0 ? property.getSaveSize() : 0))).build()
            ).execute();
        } catch (Exception t) {
            if (FetchStatus.PAUSE.equals(property.getSaveStatus())) {
                try {
                    subscriber.onCompleted();
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    SaveExecutor.reportPause(property);
                }
            } else {
                recorder.updateSaveStatus(property, FetchStatus.ERROR);
                try {
                    subscriber.onError(new SaveException(property.getTaskId(), FetchError.REQUEST_ERR.getCode(), FetchError.REQUEST_ERR.getMessage(), t));
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e("FetchService save task<" + property.getTaskId() + ">: request remote data error.", t);
                    }
                }
            }
            return;
        }
        // 处理应答
        if (httpResponse == null || !httpResponse.isSuccessful()) {
            if (saveRetry > 0) {
                try {
                    Thread.sleep(SAVE_DORMANT);
                } catch (Exception p) {
                    if (FetchStatus.PAUSE.equals(property.getSaveStatus())) {
                        try {
                            subscriber.onCompleted();
                        } catch (Exception e) {
                            if (Lc.E) {
                                Lc.t(SproutLib.name).e(e);
                            }
                        } finally {
                            SaveExecutor.reportPause(property);
                        }
                    } else {
                        recorder.updateSaveStatus(property, FetchStatus.ERROR);
                        try {
                            subscriber.onError(new SaveException(property.getTaskId(), FetchError.RETRY_ERR.getCode(), FetchError.RETRY_ERR.getMessage(), p));
                        } catch (Exception e) {
                            if (Lc.E) {
                                Lc.t(SproutLib.name).e(e);
                            }
                        } finally {
                            if (Lc.E) {
                                Lc.t(SproutLib.name).e("FetchService save task<" + property.getTaskId() + ">: request retry error.", p);
                            }
                        }
                    }
                    return;
                }
                // 自动重试
                SaveExecutor.downloadFile(recorder, property, subscriber, saveRetry - 1, saveFile, tempFile);
            } else {
                // 网络请求应答失败
                recorder.updateSaveStatus(property, FetchStatus.ERROR);
                try {
                    subscriber.onCompleted();
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    SaveExecutor.reportError(property.getTaskId(), FetchError.REQUEST_ERR);
                }
            }
            return;
        }
        // 数据校验
        long fileSize = httpResponse.body().contentLength();
        if (fileSize <= 0) {
            // 远程文件大小异常
            recorder.updateSaveStatus(property, FetchStatus.ERROR);
            try {
                subscriber.onCompleted();
            } catch (Exception e) {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(e);
                }
            } finally {
                SaveExecutor.reportError(property.getTaskId(), FetchError.RESPFILE_ERR);
            }
            return;
        }
        // 断点续传
        long fstmp = property.getSaveSize(), total = 0L;
        if (property.getFileSize() > 0 && property.getFileSize() != fileSize) {
            if ((property.getFileSize() - fstmp) != fileSize) {
                // 远程文件大小校验
                recorder.updateSaveStatus(property, FetchStatus.ERROR);
                try {
                    subscriber.onCompleted();
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    SaveExecutor.reportError(property.getTaskId(), FetchError.FILESIZE_CONFLICT_ERR);
                }
                return;
            }
            // 断点续传设置
            fileSize += (total = fstmp);
        }
        // 保存下载文件
        final RandomAccessFile outer;
        try {
            (outer = new RandomAccessFile(tempFile, MODE_OUTPUT)).seek(total);
        } catch (Exception t) {
            recorder.updateSaveStatus(property, FetchStatus.ERROR);
            try {
                subscriber.onError(new SaveException(property.getTaskId(), FetchError.TEMPFILE_CHAN_ERR.getCode(), FetchError.TEMPFILE_CHAN_ERR.getMessage(), t));
            } catch (Exception e) {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(e);
                }
            } finally {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e("FetchService save task<" + property.getTaskId() + ">: open temp file error.", t);
                }
            }
            return;
        }
        if (outer != null) {
            // 首次下载文件
            if (property.getFileSize() < 1) {
                recorder.updateFileSize(property, fileSize);
            }
            // 更新下载状态
            SaveExecutor.reportStart(recorder.updateSaveStatus(property, FetchStatus.START));
        }
        // 写入下载文件
        final byte[] buf = new byte[SIZE_BUFFER];
        final BufferedInputStream bis = new BufferedInputStream(httpResponse.body().byteStream(), SIZE_BUFFER);
        try {
            int count, begin;
            while ((count = bis.read(buf, 0, buf.length)) != -1) {
                if (fstmp == total) {
                    begin = 0;
                    total += count;
                } else {
                    total += count;
                    if (fstmp > total) {
                        continue;
                    } else {
                        begin = Long.valueOf(fstmp + count - total).intValue();
                    }
                }
                count -= begin;
                fstmp += count;
                outer.write(buf, begin, count);
                subscriber.onNext(recorder.updateSaveSize(property, fstmp));
            }
        } catch (Exception t) {
            if (FetchStatus.PAUSE.equals(property.getSaveStatus())) {
                try {
                    subscriber.onCompleted();
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    SaveExecutor.reportPause(property);
                }
            } else {
                recorder.updateSaveStatus(property, FetchStatus.ERROR);
                try {
                    subscriber.onError(new SaveException(property.getTaskId(), FetchError.WRITE_ERR.getCode(), FetchError.WRITE_ERR.getMessage(), t));
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e("FetchService save task<" + property.getTaskId() + ">: write save data error.", t);
                    }
                }
            }
            return;
        } finally {
            try {
                bis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                outer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 校验是否完成
        if (property.getSaveSize() == fileSize && property.getFileSize() == fileSize) {
            // 临时文件转换
            if (tempFile.renameTo(saveFile)) {
                recorder.updateSaveStatus(property, FetchStatus.FINISH);
                try {
                    subscriber.onCompleted();
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    SaveExecutor.reportFinish(property);
                }
            } else {
                // 文件转换失败
                recorder.updateSaveStatus(property, FetchStatus.ERROR);
                try {
                    subscriber.onCompleted();
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    SaveExecutor.reportError(property.getTaskId(), FetchError.TEMPFILE_CONVERT_ERR);
                }
            }
        } else {
            // 下载未完
            if (FetchStatus.PAUSE.equals(property.getSaveStatus())) {
                try {
                    subscriber.onCompleted();
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    SaveExecutor.reportPause(property);
                }
            } else {
                recorder.updateSaveStatus(property, FetchStatus.ERROR);
                try {
                    subscriber.onCompleted();
                } catch (Exception e) {
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    SaveExecutor.reportError(property.getTaskId(), FetchError.SAVESIZE_LESS_ERR);
                }
            }
        }
    }

}
