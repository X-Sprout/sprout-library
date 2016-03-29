/**
 * Created on 2016/3/28
 */
package org.sprout.cache.disk;

import org.sprout.SproutLib;
import org.sprout.cache.CacheSource;
import org.sprout.cache.base.CacheConfig;
import org.sprout.cache.base.CacheHandle;
import org.sprout.cache.conf.StoreConfig;
import org.sprout.core.assist.StringUtils;
import org.sprout.core.logging.Lc;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 持久存储源类
 * <p/>
 *
 * @author Wythe
 */
public final class DiskLruSource<TYPE> extends CacheSource<TYPE> {

    private final static Map<String, DiskLruHandle> SOURCES = new ConcurrentHashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    protected CacheHandle<TYPE> open(final Class<TYPE> clazz, final CacheConfig setup) {
        final StoreConfig config;
        if (StoreConfig.class.isInstance(setup)) {
            final File file = (config = (StoreConfig) setup).getFile();
            if (file != null) {
                final String path = file.getAbsolutePath();
                if (!StringUtils.isEmpty(path)) {
                    DiskLruHandle<TYPE> handle = SOURCES.get(path);
                    if (handle != null) {
                        if (handle.mDiskLruGenre == clazz) {
                            if (handle.alive()) {
                                return handle;
                            } else {
                                SOURCES.remove(path);
                            }
                        } else {
                            if (Lc.E) {
                                Lc.t(SproutLib.name).e("CacheService unable to open the \"" + clazz + "\" data type of store in \"" + path + "\"");
                            }
                            return null;
                        }
                    }
                    // 创建实例
                    DiskLruCache cache = null;
                    try {
                        cache = DiskLruCache.open(file, config.getVern(), 1, config.getSize());
                    } catch (Exception e) {
                        cache = null;
                        if (Lc.E) {
                            Lc.t(SproutLib.name).e(e);
                        }
                    } finally {
                        if (cache == null) {
                            handle = null;
                        } else {
                            SOURCES.put(path, (handle = new DiskLruHandle<>(clazz, cache)));
                        }
                    }
                    return handle;
                }
            }
        }
        return null;
    }

}
