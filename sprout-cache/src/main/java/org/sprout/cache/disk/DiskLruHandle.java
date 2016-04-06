/**
 * Created on 2016/3/23
 */
package org.sprout.cache.disk;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import org.sprout.SproutLib;
import org.sprout.cache.base.CacheHandle;
import org.sprout.core.assist.ArrayUtils;
import org.sprout.core.assist.ImageUtils;
import org.sprout.core.assist.SerialUtils;
import org.sprout.core.assist.StringUtils;
import org.sprout.core.logging.Lc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

/**
 * 持久存储操作类
 * <p/>
 *
 * @author Wythe
 */
public final class DiskLruHandle<TYPE> implements CacheHandle<TYPE> {

    // 块区大小
    private final static int BUF_LONG = 8;
    private final static int BUF_SIZE = 1024;

    // 缓存类型
    final Class<TYPE> mDiskLruGenre;
    // 缓存句柄
    final DiskLruCache mDiskLruCache;

    DiskLruHandle(final Class<TYPE> mDiskLruGenre, final DiskLruCache mDiskLruCache) {
        this.mDiskLruGenre = mDiskLruGenre;
        this.mDiskLruCache = mDiskLruCache;
    }

    @Override
    public void close() throws IOException {
        if (this.mDiskLruCache != null) {
            this.mDiskLruCache.close();
        }
    }

    @Override
    public void flush() throws IOException {
        if (this.mDiskLruCache != null) {
            this.mDiskLruCache.flush();
        }
    }

    @Override
    public void clear() throws IOException {
        if (this.mDiskLruCache != null) {
            this.mDiskLruCache.delete();
        }
    }

    @Override
    public boolean alive() {
        return this.mDiskLruCache != null && !this.mDiskLruCache.isClosed();
    }

    @Override
    public boolean exists(final String key) {
        return !this.expire(key);
    }

    @Override
    public boolean expire(final String key) {
        if (this.mDiskLruCache != null && !StringUtils.isEmpty(key)) {
            final ByteBuffer buf = this.getBytes(key);
            if (buf != null && buf.limit() > 0) {
                final long out = buf.getLong();
                if (out == 0 || out > System.currentTimeMillis()) {
                    return false;
                } else {
                    this.del(key);
                }
            }
        }
        return true;
    }

    @Override
    public TYPE remove(final String key) {
        final TYPE wipe = this.get(key);
        if (wipe != null) {
            try {
                this.mDiskLruCache.remove(key);
            } catch (Exception e) {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(e);
                }
                return null;
            }
        }
        return wipe;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TYPE get(final String key) {
        if (this.mDiskLruCache != null && this.mDiskLruGenre != null && !StringUtils.isEmpty(key)) {
            final ByteBuffer buf = this.getBytes(key);
            if (buf != null && buf.limit() > 0) {
                // 是否过期
                final long out = buf.getLong();
                if (out > 0 && System.currentTimeMillis() >= out) {
                    this.del(key);
                } else {
                    final int len;
                    final byte[] val;
                    if ((len = buf.remaining()) > 0) {
                        buf.get(val = new byte[len]);
                        if (!ArrayUtils.isEmpty(val)) {
                            if (Byte.class.isAssignableFrom(this.mDiskLruGenre)) {
                                try {
                                    return (TYPE) ((Byte) val[0]);
                                } catch (ClassCastException e) {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e(e);
                                    }
                                }
                                return null;
                            }
                            if (byte[].class.isAssignableFrom(this.mDiskLruGenre)) {
                                try {
                                    return (TYPE) val;
                                } catch (ClassCastException e) {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e(e);
                                    }
                                }
                                return null;
                            }
                            if (String.class.isAssignableFrom(this.mDiskLruGenre)) {
                                try {
                                    return (TYPE) new String(val);
                                } catch (ClassCastException e) {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e(e);
                                    }
                                }
                                return null;
                            }
                            if (Byte[].class.isAssignableFrom(this.mDiskLruGenre)) {
                                try {
                                    return (TYPE) ArrayUtils.toObject(val);
                                } catch (ClassCastException e) {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e(e);
                                    }
                                }
                                return null;
                            }
                            if (Bitmap.class.isAssignableFrom(this.mDiskLruGenre)) {
                                try {
                                    return (TYPE) ImageUtils.bytes2Bitmap(val);
                                } catch (ClassCastException e) {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e(e);
                                    }
                                }
                                return null;
                            }
                            if (Drawable.class.isAssignableFrom(this.mDiskLruGenre)) {
                                try {
                                    return (TYPE) ImageUtils.bitmap2Drawable(ImageUtils.bytes2Bitmap(val));
                                } catch (ClassCastException e) {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e(e);
                                    }
                                }
                                return null;
                            }
                            try {
                                return SerialUtils.decode(val, this.mDiskLruGenre);
                            } catch (ClassCastException e) {
                                if (Lc.E) {
                                    Lc.t(SproutLib.name).e(e);
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public void del(final String key) {
        if (this.mDiskLruCache != null && !StringUtils.isEmpty(key)) {
            try {
                this.mDiskLruCache.remove(key);
            } catch (Exception e) {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(e);
                }
            }
        }
    }

    @Override
    public void set(final String key, final int out) {
        if (this.mDiskLruCache != null && !StringUtils.isEmpty(key) && out >= 0) {
            final ByteBuffer buf = this.getBytes(key);
            if (buf != null && buf.limit() > 0) {
                final long old = buf.getLong();
                if (old > 0 && System.currentTimeMillis() >= old) {
                    this.del(key);
                } else {
                    final int len;
                    final byte[] val;
                    if ((len = buf.remaining()) > 0) {
                        buf.get(val = new byte[len]);
                        // 更新超时
                        this.putBytes(key, this.setExpire(out, val));
                    }
                }
            }
        }
    }

    @Override
    public void put(final String key, final TYPE val) {
        this.put(key, val, 0);
    }

    @Override
    public void put(final String key, final TYPE val, final int out) {
        if (this.mDiskLruCache != null && !StringUtils.isEmpty(key) && val != null && out >= 0) {
            if (Byte.class.isInstance(val)) {
                this.putBytes(key, this.setExpire(out, (Byte) val));
                return;
            }
            if (byte[].class.isInstance(val)) {
                this.putBytes(key, this.setExpire(out, (byte[]) val));
                return;
            }
            if (String.class.isInstance(val)) {
                this.putBytes(key, this.setExpire(out, ((String) val).getBytes()));
                return;
            }
            if (Byte[].class.isInstance(val)) {
                this.putBytes(key, this.setExpire(out, ArrayUtils.toPrimitive((Byte[]) val)));
                return;
            }
            if (Bitmap.class.isInstance(val)) {
                this.putBytes(key, this.setExpire(out, ImageUtils.bitmap2Bytes((Bitmap) val, Bitmap.CompressFormat.PNG)));
                return;
            }
            if (Drawable.class.isInstance(val)) {
                this.putBytes(key, this.setExpire(out, ImageUtils.bitmap2Bytes(ImageUtils.drawable2Bitmap((Drawable) val), Bitmap.CompressFormat.PNG)));
                return;
            }
            this.putBytes(key, this.setExpire(out, SerialUtils.encode(val)));
        }
    }

    // 写入字节流
    private void putBytes(final String key, final ByteBuffer buf) {
        if (!StringUtils.isEmpty(key) && buf != null) {
            final int l = buf.limit();
            if (l > 0) {
                final DiskLruCache.Editor editor = this.getEditor(key);
                if (editor != null) {
                    OutputStream stream = null;
                    try {
                        stream = editor.newOutputStream(0);
                    } catch (Exception e) {
                        stream = null;
                        if (Lc.E) {
                            Lc.t(SproutLib.name).e(e);
                        }
                    } finally {
                        final byte[] bytes;
                        if (stream != null) {
                            buf.get(bytes = new byte[l]);
                            try {
                                stream.write(
                                    bytes
                                );
                                stream.flush();
                                editor.commit();
                            } catch (Exception e) {
                                try {
                                    editor.abort();
                                } catch (Exception t) {
                                    t.printStackTrace();
                                } finally {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e(e);
                                    }
                                }
                            } finally {
                                try {
                                    stream.close();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // 查询字节流
    private ByteBuffer getBytes(final String key) {
        if (!StringUtils.isEmpty(key)) {
            final InputStream stream = this.getStream(key);
            if (stream != null) {
                final ByteArrayOutputStream output = new ByteArrayOutputStream(BUF_SIZE);
                try {
                    int len;
                    final byte[] tmp = new byte[BUF_SIZE];
                    while ((len = stream.read(tmp)) != -1) {
                        output.write(tmp, 0, len);
                    }
                } catch (Exception e) {
                    output.reset();
                    if (Lc.E) {
                        Lc.t(SproutLib.name).e(e);
                    }
                } finally {
                    try {
                        output.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        stream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // 字节格式化
                final byte[] bytes = output.toByteArray();
                if (bytes.length > 0) {
                    return ByteBuffer.wrap(bytes);
                }
            }
        }
        return null;
    }

    // 获得输入流
    private InputStream getStream(final String key) {
        if (this.mDiskLruCache != null) {
            DiskLruCache.Snapshot snapshot;
            try {
                snapshot = this.mDiskLruCache.get(key);
            } catch (Exception e) {
                snapshot = null;
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(e);
                }
            }
            if (snapshot != null) {
                return snapshot.getInputStream(0);
            }
        }
        return null;
    }

    // 获得编辑器
    private DiskLruCache.Editor getEditor(final String key) {
        if (this.mDiskLruCache != null) {
            try {
                return this.mDiskLruCache.edit(key);
            } catch (Exception e) {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(e);
                }
            }
        }
        return null;
    }

    // 设置过期时间
    private ByteBuffer setExpire(final int out, final byte... val) {
        if (val != null) {
            final int len = val.length;
            if (len > 0) {
                final ByteBuffer buf = ByteBuffer.allocate(BUF_LONG + len).putLong(out > 0 ? System.currentTimeMillis() + out : 0L).put(val);
                if (buf != null) {
                    buf.flip();
                }
                return buf;
            }
        }
        return null;
    }

}
