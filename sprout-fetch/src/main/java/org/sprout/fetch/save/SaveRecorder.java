/**
 * Created on 2016/3/3
 */
package org.sprout.fetch.save;

import org.sprout.cache.CacheService;
import org.sprout.cache.base.CacheHandle;
import org.sprout.cache.conf.StoreConfig;
import org.sprout.core.assist.StringUtils;
import org.sprout.fetch.FetchService;
import org.sprout.fetch.spec.FetchStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 下载记录工具
 * <p/>
 *
 * @author Wythe
 */
public final class SaveRecorder {

    // 下载服务标识
    private static final String SAVE_ID_KEY = "_$SAVE_ID";
    // 网络文件地址
    private static final String SAVE_URL_KEY = "_$SAVE_URL";
    // 下载文件大小
    private static final String SAVE_SIZE_KEY = "_$SAVE_SIZE";
    // 下载文件时间
    private static final String SAVE_TIME_KEY = "_$SAVE_TIME";
    // 保存文件路径
    private static final String SAVE_PATH_KEY = "_$SAVE_PATH";
    // 网络文件大小
    private static final String FILE_SIZE_KEY = "_$FILE_SIZE";
    // 下载重试次数
    private static final String SAVE_RETRY_KEY = "_$SAVE_RETRY";
    // 下载优先等级
    private static final String SAVE_PRIOR_KEY = "_$SAVE_PRIOR";
    // 下载文件状态
    private static final String SAVE_STATUS_KEY = "_$SAVE_STATUS";
    // 下载超时时长
    private static final String SAVE_TIMEOUT_KEY = "_$SAVE_TIMEOUT";

    final CacheHandle<Map> mCacheHandle;

    SaveRecorder(final String directory) throws IOException {
        this.mCacheHandle = CacheService.store(Map.class, StoreConfig.apply(FetchService.getRecordPath() + directory).setSize(Integer.MAX_VALUE).build());
    }

    /**
     * 是否关闭缓存
     *
     * @return 是否关闭
     * @author Wythe
     */
    boolean isShut() {
        return this.mCacheHandle == null || !this.mCacheHandle.alive();
    }

    /**
     * 删除下载记录
     *
     * @param saveId 下载标识
     * @author Cuzki
     */
    void removeRecorder(final String saveId) {
        if (!StringUtils.isEmpty(saveId) && !this.isShut()) {
            this.mCacheHandle.del(saveId);
        }
    }

    /**
     * 查询下载记录
     *
     * @param saveId 下载标识
     * @return 下载属性
     * @author Cuzki
     */
    @SuppressWarnings("unchecked")
    SaveProperty selectRecorder(final String saveId) {
        if (this.isShut() || StringUtils.isEmpty(saveId)) {
            return null;
        }
        return decapsulate(this.mCacheHandle.get(saveId));
    }

    /**
     * 新建任务记录，无论之前是否有此任务记录，用于某些异常情况下（临时文件缺失等）任务记录归零
     *
     * @param saveScheduler 任务计划
     * @return 下载属性
     * @author Cuzki
     */
    SaveProperty insertScheduler(final SaveScheduler saveScheduler) {
        if (saveScheduler != null && !StringUtils.isEmpty(saveScheduler.saveId) && !StringUtils.isEmpty(saveScheduler.saveUrl) && !StringUtils.isEmpty(saveScheduler.savePath)) {
            if (!this.isShut()) {
                final SaveProperty saveProperty = new SaveProperty(saveScheduler.saveId);
                saveProperty.setSaveTime(System.currentTimeMillis());
                saveProperty.setSaveStatus(FetchStatus.AWAIT);
                saveProperty.setSaveUrl(saveScheduler.saveUrl);
                saveProperty.setSavePath(saveScheduler.savePath);
                saveProperty.setSaveRetry(saveScheduler.saveRetry);
                saveProperty.setSavePrior(saveScheduler.savePrior);
                saveProperty.setSaveTimeout(saveScheduler.saveTimeout);
                this.mCacheHandle.put(saveScheduler.saveId, encapsulate(saveProperty));
                return saveProperty;
            }
        }
        return null;
    }

    /**
     * 还原任务记录
     *
     * @param saveProperty 下载属性
     * @return 下载属性
     * @author Wythe
     */
    SaveProperty revertScheduler(final SaveProperty saveProperty, final SaveScheduler saveScheduler) {
        if (saveProperty != null) {
            boolean amendRecord = false;
            if (!StringUtils.isEmpty(saveScheduler.saveUrl) && !saveScheduler.saveUrl.equals(saveProperty.getSaveUrl())) {
                saveProperty.setSaveUrl(saveScheduler.saveUrl);
                amendRecord = true;
            }
            if (saveScheduler.savePrior >= 0 && saveScheduler.savePrior != saveProperty.getSavePrior().getValue()) {
                saveProperty.setSavePrior(saveScheduler.savePrior);
                amendRecord = true;
            }
            if (saveScheduler.saveTimeout >= 0 && saveScheduler.saveTimeout != saveProperty.getSaveTimeout()) {
                saveProperty.setSaveTimeout(saveScheduler.saveTimeout);
                amendRecord = true;
            }
            if (saveScheduler.saveRetry >= 0 && saveScheduler.saveRetry != saveProperty.getSaveRetry()) {
                saveProperty.setSaveRetry(saveScheduler.saveRetry);
                amendRecord = true;
            }
            if (!FetchStatus.AWAIT.equals(saveProperty.getSaveStatus())) {
                saveProperty.setSaveStatus(FetchStatus.AWAIT);
                amendRecord = true;
            }
            if (saveProperty.getFileSize() != 0) {
                saveProperty.setFileSize(0);
                amendRecord = true;
            }
            if (saveProperty.getSaveSize() != 0) {
                saveProperty.setSaveSize(0);
                amendRecord = true;
            }
            if (amendRecord) {
                this.mCacheHandle.put(saveProperty.getTaskId(), encapsulate(saveProperty));
            }
        }
        return saveProperty;
    }

    /**
     * 更新本地任务记录：1.如无任务记录，则新建任务记录并存入参数；2本地有记录则只更新url和savepath
     *
     * @param saveProperty  下载属性
     * @param saveScheduler 任务计划
     * @return 下载属性
     * @author Cuzki
     */
    @SuppressWarnings("unchecked")
    SaveProperty updateScheduler(final SaveProperty saveProperty, final SaveScheduler saveScheduler) {
        if (saveScheduler != null && !StringUtils.isEmpty(saveScheduler.saveId)) {
            if (saveProperty == null) {
                return this.insertScheduler(saveScheduler);
            }
            if (!this.isShut()) {
                boolean amendRecord = false;
                if (!StringUtils.isEmpty(saveScheduler.saveUrl) && !saveScheduler.saveUrl.equals(saveProperty.getSaveUrl())) {
                    saveProperty.setSaveUrl(saveScheduler.saveUrl);
                    amendRecord = true;
                }
                if (saveScheduler.savePrior >= 0 && saveScheduler.savePrior != saveProperty.getSavePrior().getValue()) {
                    saveProperty.setSavePrior(saveScheduler.savePrior);
                    amendRecord = true;
                }
                if (saveScheduler.saveTimeout >= 0 && saveScheduler.saveTimeout != saveProperty.getSaveTimeout()) {
                    saveProperty.setSaveTimeout(saveScheduler.saveTimeout);
                    amendRecord = true;
                }
                if (saveScheduler.saveRetry >= 0 && saveScheduler.saveRetry != saveProperty.getSaveRetry()) {
                    saveProperty.setSaveRetry(saveScheduler.saveRetry);
                    amendRecord = true;
                }
                if (!FetchStatus.AWAIT.equals(saveProperty.getSaveStatus())) {
                    saveProperty.setSaveStatus(FetchStatus.AWAIT);
                    amendRecord = true;
                }
                if (amendRecord) {
                    this.mCacheHandle.put(saveScheduler.saveId, encapsulate(saveProperty));
                }
            }
            return saveProperty;
        }
        return null;
    }

    /**
     * 更新下载进度
     *
     * @param saveId   下载标识
     * @param downSize 新增大小
     * @return 下载属性
     * @author Cuzki
     */
    @SuppressWarnings("unchecked")
    SaveProperty updateProgress(final String saveId, final int downSize) {
        return StringUtils.isEmpty(saveId) || this.isShut() ? null : (
                this.updateProgress(decapsulate(this.mCacheHandle.get(saveId)), downSize)
        );
    }

    /**
     * 更新下载进度
     *
     * @param saveProperty 下载属性
     * @param downSize     新增大小
     * @return 下载属性
     * @author Wythe
     */
    SaveProperty updateProgress(final SaveProperty saveProperty, final int downSize) {
        if (saveProperty != null && downSize > 0) {
            saveProperty.addSaveSize(downSize);
            if (!StringUtils.isEmpty(saveProperty.getTaskId()) && !this.isShut()) {
                this.mCacheHandle.put(saveProperty.getTaskId(), encapsulate(saveProperty));
            }
        }
        return saveProperty;
    }

    /**
     * 更新文件大小
     *
     * @param saveId   下载标识
     * @param saveSize 下载大小
     * @return 下载属性
     * @author Cuzki
     */
    @SuppressWarnings("unchecked")
    SaveProperty updateSaveSize(final String saveId, final long saveSize) {
        return StringUtils.isEmpty(saveId) || this.isShut() ? null : (
                this.updateSaveSize(decapsulate(this.mCacheHandle.get(saveId)), saveSize)
        );
    }

    /**
     * 更新下载状态
     *
     * @param saveProperty 下载属性
     * @param saveSize     下载大小
     * @return 下载属性
     * @author Wythe
     */
    SaveProperty updateSaveSize(final SaveProperty saveProperty, final long saveSize) {
        if (saveProperty != null && saveSize >= 0 && saveSize != saveProperty.getSaveSize()) {
            saveProperty.setSaveSize(saveSize);
            if (!StringUtils.isEmpty(saveProperty.getTaskId()) && !this.isShut()) {
                this.mCacheHandle.put(saveProperty.getTaskId(), encapsulate(saveProperty));
            }
        }
        return saveProperty;
    }

    /**
     * 更新文件大小
     *
     * @param saveId   下载标识
     * @param fileSize 文件大小
     * @return 下载属性
     * @author Cuzki
     */
    @SuppressWarnings("unchecked")
    SaveProperty updateFileSize(final String saveId, final long fileSize) {
        return StringUtils.isEmpty(saveId) || this.isShut() ? null : (
                this.updateFileSize(decapsulate(this.mCacheHandle.get(saveId)), fileSize)
        );
    }

    /**
     * 更新下载状态
     *
     * @param saveProperty 下载属性
     * @param fileSize     文件大小
     * @return 下载属性
     * @author Wythe
     */
    SaveProperty updateFileSize(final SaveProperty saveProperty, final long fileSize) {
        if (saveProperty != null && fileSize >= 0 && fileSize != saveProperty.getFileSize()) {
            saveProperty.setFileSize(fileSize);
            if (!StringUtils.isEmpty(saveProperty.getTaskId()) && !this.isShut()) {
                this.mCacheHandle.put(saveProperty.getTaskId(), encapsulate(saveProperty));
            }
        }
        return saveProperty;
    }

    /**
     * 更新下载状态
     *
     * @param saveId     下载标识
     * @param saveStatus 下载状态
     * @return 下载属性
     * @author Cuzki
     */
    @SuppressWarnings("unchecked")
    SaveProperty updateSaveStatus(final String saveId, final FetchStatus saveStatus) {
        return StringUtils.isEmpty(saveId) || this.isShut() ? null : (
                this.updateSaveStatus(decapsulate(this.mCacheHandle.get(saveId)), saveStatus)
        );
    }

    /**
     * 更新下载状态
     *
     * @param saveProperty 下载属性
     * @param saveStatus   下载状态
     * @return 下载属性
     * @author Wythe
     */
    SaveProperty updateSaveStatus(final SaveProperty saveProperty, final FetchStatus saveStatus) {
        if (saveProperty != null && saveStatus != null && !saveStatus.equals(saveProperty.getSaveStatus())) {
            saveProperty.setSaveStatus(saveStatus);
            if (!StringUtils.isEmpty(saveProperty.getTaskId()) && !this.isShut()) {
                this.mCacheHandle.put(saveProperty.getTaskId(), encapsulate(saveProperty));
            }
        }
        return saveProperty;
    }

    public static SaveProperty decapsulate(final Map<String, String> map) {
        if (map != null) {
            final String saveId = map.get(SAVE_ID_KEY);
            if (!StringUtils.isEmpty(saveId)) {
                SaveProperty property = new SaveProperty(saveId);
                try {
                    property.setSaveTime(Long.valueOf(map.get(SAVE_TIME_KEY)));
                    property.setFileSize(Long.valueOf(map.get(FILE_SIZE_KEY)));
                    property.setSaveSize(Long.valueOf(map.get(SAVE_SIZE_KEY)));
                    property.setSaveRetry(Integer.valueOf(map.get(SAVE_RETRY_KEY)));
                    property.setSavePrior(Integer.valueOf(map.get(SAVE_PRIOR_KEY)));
                    property.setSaveStatus(Integer.valueOf(map.get(SAVE_STATUS_KEY)));
                    property.setSaveTimeout(Integer.valueOf(map.get(SAVE_TIMEOUT_KEY)));
                } catch (NumberFormatException e) {
                    property = null;
                } finally {
                    if (property != null) {
                        property.setSaveUrl(map.get(SAVE_URL_KEY));
                        property.setSavePath(map.get(SAVE_PATH_KEY));
                    }
                }
                return property;
            }
        }
        return null;
    }

    public static HashMap<String, String> encapsulate(final SaveProperty property) {
        if (property != null) {
            final String saveId = property.getTaskId();
            if (!StringUtils.isEmpty(saveId)) {
                final HashMap<String, String> map = new HashMap<>();
                map.put(SAVE_ID_KEY, saveId);
                map.put(SAVE_URL_KEY, property.getSaveUrl());
                map.put(SAVE_PATH_KEY, property.getSavePath());
                map.put(SAVE_TIME_KEY, String.valueOf(property.getSaveTime()));
                map.put(FILE_SIZE_KEY, String.valueOf(property.getFileSize()));
                map.put(SAVE_SIZE_KEY, String.valueOf(property.getSaveSize()));
                map.put(SAVE_RETRY_KEY, String.valueOf(property.getSaveRetry()));
                map.put(SAVE_PRIOR_KEY, String.valueOf(property.getSavePrior().getValue()));
                map.put(SAVE_STATUS_KEY, String.valueOf(property.getSaveStatus().getValue()));
                map.put(SAVE_TIMEOUT_KEY, String.valueOf(property.getSaveTimeout()));
                return map;
            }
        }
        return null;
    }

}
