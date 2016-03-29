/**
 * Created on 2016/2/25
 */
package org.sprout.fetch;

import android.content.Context;

import org.sprout.SproutLib;

/**
 * 网络服务类
 * <p/>
 *
 * @author Wythe
 */
public final class FetchService {

    // 网路服务选项
    private static FetchOptions mFetchOptions = new FetchOptions.Builder().build();

    /**
     * 初始化网络服务
     * <p/>
     *
     * @param options 网络服务选项
     * @author Wythe
     */
    public static void init(final FetchOptions options) {
        final Context context = SproutLib.getContext();
        if (context != null && options != null) {
            FetchService.mFetchOptions = options;
        }
    }

    /**
     * 停止网络服务
     * <p/>
     *
     * @author Wythe
     */
    public static void stop() {
        SaveService.Operator.Instance.destroy();
    }

    /**
     * 获取记录目录地址
     * <p/>
     *
     * @return 记录目录地址
     * @author Wythe
     */
    public static String getRecordPath() {
        return FetchService.mFetchOptions.getRecordPath();
    }

    /**
     * 获取请求队列数
     * <p/>
     *
     * @return 请求队列数
     * @author Wythe
     */
    public static int getTakeQueue() {
        return FetchService.mFetchOptions.getTakeQueue();
    }

    /**
     * 获取下载队列数
     * <p/>
     *
     * @return 下载队列数
     * @author Wythe
     */
    public static int getSaveQueue() {
        return FetchService.mFetchOptions.getSaveQueue();
    }

    /**
     * 获取上传队列数
     * <p/>
     *
     * @return 下载队列数
     * @author Wythe
     */
    public static int getGiveQueue() {
        return FetchService.mFetchOptions.getGiveQueue();
    }

    /**
     * 获取请求线程数
     * <p/>
     *
     * @return 请求线程数
     * @author Wythe
     */
    public static int getTakeThread() {
        return FetchService.mFetchOptions.getTakeThread();
    }

    /**
     * 获取下载线程数
     * <p/>
     *
     * @return 下载线程数
     * @author Wythe
     */
    public static int getSaveThread() {
        return FetchService.mFetchOptions.getSaveThread();
    }

    /**
     * 获取上传线程数
     * <p/>
     *
     * @return 上传线程数
     * @author Wythe
     */
    public static int getGiveThread() {
        return FetchService.mFetchOptions.getGiveThread();
    }

    /**
     * 设置请求线程数
     * <p/>
     *
     * @param takeThread 请求线程数
     * @author Wythe
     */
    public static void setTakeThread(final int takeThread) {
        if (takeThread > 0) {
            FetchService.mFetchOptions.takeThread = takeThread;
        }
    }

    /**
     * 设置下载线程数
     * <p/>
     *
     * @param saveThread 下载线程数
     * @author Wythe
     */
    public static void setSaveThread(final int saveThread) {
        if (saveThread > 0) {
            FetchService.mFetchOptions.saveThread = saveThread;
        }
    }

    /**
     * 设置上传线程数
     * <p/>
     *
     * @param giveThread 上传线程数
     * @author Wythe
     */
    public static void setGiveThread(final int giveThread) {
        if (giveThread > 0) {
            FetchService.mFetchOptions.giveThread = giveThread;
        }
    }

    /**
     * 查找下载任务
     * <p/>
     *
     * @param saveId 下载标识
     * @return 下载服务期望
     * @author Wythe
     */
    public static SaveService.Promiser findSave(final String saveId) {
        return SaveService.Promiser.create(saveId);
    }

    /**
     * 调用下载服务
     * <p/>
     *
     * @param saveId  下载标识
     * @param saveUrl 下载地址
     * @return 下载服务
     * @author Wythe
     */
    public static SaveService save(final String saveId, final String saveUrl) {
        return new SaveService(saveId, saveUrl);
    }

}
