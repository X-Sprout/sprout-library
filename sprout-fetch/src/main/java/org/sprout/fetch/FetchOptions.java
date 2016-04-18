/**
 * Created on 2016/2/25
 */
package org.sprout.fetch;

import org.sprout.SproutLib;
import org.sprout.core.assist.StoreUtils;
import org.sprout.core.assist.StringUtils;

import java.io.File;

/**
 * 网络服务选项类
 * <p/>
 *
 * @author Wythe
 */
public final class FetchOptions {

    private final static String FETCH_RECORD = "record-fetch";

    // 请求线程大小
    int takeThread = 3;
    // 下载线程大小
    int saveThread = 3;
    // 上传线程大小
    int giveThread = 3;
    // 请求队列大小
    private int takeQueue = 300;
    // 下载队列大小
    private int saveQueue = 300;
    // 上传队列大小
    private int giveQueue = 300;
    // 任务记录地址
    private String recordPath = null;

    private FetchOptions(final String recordPath) {
        this.recordPath = recordPath;
    }

    public int getTakeThread() {
        return this.takeThread;
    }

    public int getSaveThread() {
        return this.saveThread;
    }

    public int getGiveThread() {
        return this.giveThread;
    }

    public int getTakeQueue() {
        return this.takeQueue;
    }

    public int getSaveQueue() {
        return this.saveQueue;
    }

    public int getGiveQueue() {
        return this.giveQueue;
    }

    public String getRecordPath() {
        return recordPath;
    }

    public final static class Builder {

        private final FetchOptions fetchOptions;

        /**
         * 构造选项配置器
         * <p/>
         *
         * @author Wythe
         */
        public Builder() {
            String filesDir;
            if ((StoreUtils.isSDCardReady() || StoreUtils.isSDCardShare()) && !StoreUtils.isSDCardRemovable()) {
                filesDir = StoreUtils.getExternalFilesDir();
            } else {
                filesDir = StoreUtils.getInternalFilesDir();
            }
            this.fetchOptions = new FetchOptions(
                    (filesDir != null ? filesDir : "") + SproutLib.name + File.separator + FETCH_RECORD + File.separator
            );
        }

        /**
         * 设置请求队列大小
         * <p/>
         *
         * @param takeQueue 请求队列大小
         * @return 选项配置器
         * @author Wythe
         */
        public FetchOptions.Builder setTakeQueue(final int takeQueue) {
            if (takeQueue > 0) {
                this.fetchOptions.takeQueue = takeQueue;
            }
            return this;
        }

        /**
         * 设置下载队列大小
         * <p/>
         *
         * @param saveQueue 下载队列大小
         * @return 选项配置器
         * @author Wythe
         */
        public FetchOptions.Builder setSaveQueue(final int saveQueue) {
            if (saveQueue > 0) {
                this.fetchOptions.saveQueue = saveQueue;
            }
            return this;
        }

        /**
         * 设置上传队列大小
         * <p/>
         *
         * @param giveQueue 上传队列大小
         * @return 选项配置器
         * @author Wythe
         */
        public FetchOptions.Builder setGiveQueue(final int giveQueue) {
            if (giveQueue > 0) {
                this.fetchOptions.giveQueue = giveQueue;
            }
            return this;
        }

        /**
         * 设置请求线程大小
         * <p/>
         *
         * @param takeThread 请求线程大小
         * @return 选项配置器
         * @author Wythe
         */
        public FetchOptions.Builder setTakeThread(final int takeThread) {
            if (takeThread > 0) {
                this.fetchOptions.takeThread = takeThread;
            }
            return this;
        }

        /**
         * 设置下载线程大小
         * <p/>
         *
         * @param saveThread 下载线程大小
         * @return 选项配置器
         * @author Wythe
         */
        public FetchOptions.Builder setSaveThread(final int saveThread) {
            if (saveThread > 0) {
                this.fetchOptions.saveThread = saveThread;
            }
            return this;
        }

        /**
         * 设置上传线程大小
         * <p/>
         *
         * @param giveThread 上传线程大小
         * @return 选项配置器
         * @author Wythe
         */
        public FetchOptions.Builder setGiveThread(final int giveThread) {
            if (giveThread > 0) {
                this.fetchOptions.giveThread = giveThread;
            }
            return this;
        }

        /**
         * 设置记录目录地址
         *
         * @param recordPath 记录目录地址
         * @return 选项配置器
         * @author Wythe
         */
        public FetchOptions.Builder setRecordPath(final String recordPath) {
            if (!StringUtils.isEmpty(recordPath)) {
                this.fetchOptions.recordPath = recordPath + (
                        (!recordPath.endsWith(File.separator) ? File.separator : "") + SproutLib.name + File.separator + FETCH_RECORD + File.separator
                );
            }
            return this;
        }

        /**
         * 生成网络服务选项
         * <p/>
         *
         * @return 网络服务选项
         * @author Wythe
         */
        public FetchOptions build() {
            return this.fetchOptions;
        }

    }


}
