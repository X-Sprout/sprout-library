/**
 * Created on 2016/3/25
 */
package org.sprout.cache.conf;

import org.sprout.cache.base.CacheConfig;

/**
 * 临时存储选项类
 * <p/>
 *
 * @author Wythe
 */
public final class SpareConfig implements CacheConfig {

    @Override
    public long getSize() {
        return 0L;
    }

}
