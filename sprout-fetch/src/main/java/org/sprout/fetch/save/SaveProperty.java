package org.sprout.fetch.save;

import org.sprout.fetch.base.FetchProperty;
import org.sprout.fetch.spec.FetchPrior;
import org.sprout.fetch.spec.FetchStatus;

/**
 * 下载属性类
 * <p/>
 *
 * @author Cuzki
 */
public class SaveProperty extends FetchProperty {

    // 下载超时时长
    private int saveTimeout;
    // 下载重试次数
    private int saveRetry;
    // 下载文件时间
    private long saveTime;
    // 网络文件大小
    private long fileSize;
    // 下载文件大小
    private long saveSize;
    // 网络文件地址
    private String saveUrl;
    // 保存文件路径
    private String savePath;
    // 下载优先等级
    private FetchPrior savePrior = FetchPrior.NORM;
    // 下载文件状态
    private FetchStatus saveStatus = FetchStatus.AWAIT;

    public SaveProperty(final String saveId) {
        super(saveId);
    }

    public int getSaveTimeout() {
        return saveTimeout;
    }

    public int getSaveRetry() {
        return saveRetry;
    }

    public long getSaveTime() {
        return saveTime;
    }

    public long getFileSize() {
        return fileSize;
    }

    public long getSaveSize() {
        return saveSize;
    }

    public String getSaveUrl() {
        return saveUrl;
    }

    public String getSavePath() {
        return savePath;
    }

    public FetchPrior getSavePrior() {
        return savePrior;
    }

    public FetchStatus getSaveStatus() {
        return saveStatus;
    }

    void setSaveRetry(final int saveRetry) {
        this.saveRetry = saveRetry;
    }

    void setSaveTimeout(final int saveTimeout) {
        this.saveTimeout = saveTimeout;
    }

    void setSaveTime(long saveTime) {
        this.saveTime = saveTime;
    }

    void setFileSize(final long fileSize) {
        this.fileSize = fileSize;
    }

    void setSaveSize(final long saveSize) {
        this.saveSize = saveSize;
    }

    void addSaveSize(final long saveSize) {
        this.saveSize += saveSize;
    }

    void setSaveUrl(final String saveUrl) {
        this.saveUrl = saveUrl;
    }

    void setSavePath(final String savePath) {
        this.savePath = savePath;
    }

    void setSavePrior(final int savePrior) {
        for (final FetchPrior prior : FetchPrior.values()) {
            if (prior.getValue() == savePrior) {
                this.savePrior = prior;
                break;
            }
        }
    }

    void setSavePrior(final FetchPrior savePrior) {
        if (savePrior != null) {
            this.savePrior = savePrior;
        }
    }

    void setSaveStatus(final int saveStatus) {
        for (final FetchStatus status : FetchStatus.values()) {
            if (status.getValue() == saveStatus) {
                this.saveStatus = status;
                break;
            }
        }
    }

    void setSaveStatus(final FetchStatus saveStatus) {
        if (saveStatus != null) {
            this.saveStatus = saveStatus;
        }
    }

}
