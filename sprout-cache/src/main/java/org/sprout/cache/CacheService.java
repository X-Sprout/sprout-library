/**
 * Created on 2016/2/25
 */
package org.sprout.cache;

import org.sprout.cache.base.CacheHandle;
import org.sprout.cache.conf.ShareConfig;
import org.sprout.cache.conf.SpareConfig;
import org.sprout.cache.conf.StoreConfig;
import org.sprout.cache.disk.DiskLruSource;

/**
 * 缓存服务类
 * <p/>
 *
 * @author Wythe
 */
public final class CacheService {

    /**
     * 生成临时存储句柄
     *
     * @param clazz 临时存储类型
     * @param setup 临时存储配置
     * @return 临时存储句柄
     * @author Wythe
     */
    public static <TYPE> CacheHandle<TYPE> spare(final Class<TYPE> clazz, final SpareConfig setup) {
        return null;
    }

    /**
     * 生成共享存储句柄
     *
     * @param clazz 共享存储类型
     * @param setup 共享存储配置
     * @return 共享存储句柄
     * @author Wythe
     */
    public static <TYPE> CacheHandle<TYPE> share(final Class<TYPE> clazz, final ShareConfig setup) {
        return null;
    }

    /**
     * 生成持久存储句柄
     *
     * @param clazz 持久存储类型
     * @param setup 持久存储配置
     * @return 持久存储句柄
     * @author Wythe
     */
    public static <TYPE> CacheHandle<TYPE> store(final Class<TYPE> clazz, final StoreConfig setup) {
        return ((CacheSource<TYPE>) new DiskLruSource<TYPE>()).getHandle(clazz, setup);
    }

}
