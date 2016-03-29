/**
 * Created on 2016/3/28
 */
package org.sprout.cache;

import org.sprout.cache.base.CacheConfig;
import org.sprout.cache.base.CacheHandle;

/**
 * 缓存源类
 * <p/>
 *
 * @author Wythe
 */
public abstract class CacheSource<TYPE> {

    protected abstract CacheHandle<TYPE> open(final Class<TYPE> clazz, final CacheConfig setup);

    final CacheHandle<TYPE> getHandle(final Class<TYPE> clazz, final CacheConfig setup) {
        return this.open(clazz, setup);
    }

}
