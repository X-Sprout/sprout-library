/**
 * Created on 2016/3/5
 */
package org.sprout.fetch.save;

import java.io.Serializable;

/**
 * 下载任务计划
 * <p/>
 *
 * @author Wythe
 */
final class SaveScheduler implements Serializable {

    // 下载重试次数
    int saveRetry;
    // 下载优先等级
    int savePrior;
    // 下载超时时长
    int saveTimeout;

    // 网络文件地址
    String saveUrl;

    // 网络文件地址
    final String saveId;
    // 保存文件路径
    final String savePath;

    SaveScheduler(final String saveId, final String saveUrl, final String savePath, final int saveRetry, final int savePrior, final int saveTimeout) {
        this.saveId = saveId;
        this.saveUrl = saveUrl;
        this.savePath = savePath;
        this.saveRetry = saveRetry;
        this.savePrior = savePrior;
        this.saveTimeout = saveTimeout;
    }

}
