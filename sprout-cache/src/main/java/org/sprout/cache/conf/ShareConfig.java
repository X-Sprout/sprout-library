/**
 * Created on 2016/3/25
 */
package org.sprout.cache.conf;

import org.sprout.cache.base.CacheConfig;

/**
 * 共享存储选项类
 * <p/>
 *
 * @author Wythe
 */
public final class ShareConfig implements CacheConfig {

    @Override
    public long getSize() {
        return 0L;
    }

}
