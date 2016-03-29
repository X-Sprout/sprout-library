/**
 * Created on 2016/3/25
 */
package org.sprout.cache.conf;

import org.sprout.cache.base.CacheConfig;
import org.sprout.core.assist.StringUtils;

import java.io.File;

/**
 * 持久存储选项类
 * <p/>
 *
 * @author Wythe
 */
public final class StoreConfig implements CacheConfig {

    // 默认缓存版本
    private final static int DEF_VERN = 1;
    // 默认缓存大小(300M)
    private final static int DEF_SIZE = 0x12C00000;

    private int vern;

    private long size;

    private final File file;

    private StoreConfig(final File file, final int vern, final long size) {
        this.file = file;
        this.vern = vern;
        this.size = size;
    }

    /**
     * 获得存储版本
     *
     * @return 存储版本
     * @author Wythe
     */
    public int getVern() {
        return vern;
    }

    /**
     * 获得存储容量
     *
     * @return 存储容量
     * @author Wythe
     */
    @Override
    public long getSize() {
        return size;
    }

    /**
     * 获得存储目录
     *
     * @return 存储目录
     * @author Wythe
     */
    public File getFile() {
        return file;
    }

    /**
     * 申请存储选项配置器
     *
     * @param file 存储目录
     * @return 存储选项配置器
     * @author Wythe
     */
    public static StoreConfig.Builder apply(final File file) {
        return new StoreConfig.Builder(file);
    }

    /**
     * 申请存储选项配置器
     *
     * @param path 存储路径
     * @return 存储选项配置器
     * @author Wythe
     */
    public static StoreConfig.Builder apply(final String path) {
        return new StoreConfig.Builder(path);
    }

    /**
     * 存储选项配置类
     * <p/>
     *
     * @author Wythe
     */
    public final static class Builder {

        private final StoreConfig options;

        /**
         * 存储选项配置构造器
         *
         * @param file 存储目录
         * @author Wythe
         */
        private Builder(final File file) {
            this.options = new StoreConfig(file, DEF_VERN, DEF_SIZE);
        }

        /**
         * 存储选项配置构造器
         *
         * @param path 存储路径
         * @author Wythe
         */
        private Builder(final String path) {
            this(!StringUtils.isEmpty(path) ? new File(path) : null);
        }

        /**
         * 设置存储版本
         *
         * @param vern 存储版本
         * @return 存储选项配置
         */
        public Builder setVern(final int vern) {
            if (this.options != null && vern > 0) {
                this.options.vern = vern;
            }
            return this;
        }

        /**
         * 设置存储容量
         *
         * @param size 存储容量
         * @return 存储选项配置
         */
        public Builder setSize(final long size) {
            if (this.options != null && size > 0) {
                this.options.size = size;
            }
            return this;
        }

        /**
         * 生成存储选项配置
         * <p/>
         *
         * @return 存储选项配置
         * @author Wythe
         */
        public StoreConfig build() {
            return this.options;
        }

    }

}
