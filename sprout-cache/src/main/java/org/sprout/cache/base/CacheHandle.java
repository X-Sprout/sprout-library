/**
 * Created on 2016/3/23
 */
package org.sprout.cache.base;

import java.io.IOException;

/**
 * 缓存操作接口
 * <p/>
 *
 * @author Wythe
 */
public interface CacheHandle<TYPE> {

    void close() throws IOException;

    void flush() throws IOException;

    void clear() throws IOException;

    boolean alive();

    boolean exists(final String key);

    boolean expire(final String key);

    TYPE remove(final String key);

    TYPE get(final String key);

    void del(final String key);

    void set(final String key, final int out);

    void put(final String key, final TYPE val);

    void put(final String key, final TYPE val, final int out);

}
