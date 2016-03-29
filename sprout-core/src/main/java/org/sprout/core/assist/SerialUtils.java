/**
 * Created on 2016/2/29
 */
package org.sprout.core.assist;

import org.nustaq.serialization.FSTConfiguration;
import org.sprout.SproutLib;
import org.sprout.core.logging.Lc;

import java.io.Serializable;

/**
 * 序列化工具
 * <p/>
 *
 * @author Wythe
 */
public final class SerialUtils {

    // 序列化工具
    private final static FSTConfiguration serializer = FSTConfiguration.createAndroidDefaultConfiguration();

    /**
     * 序列化方式克隆对象
     *
     * @param obj 目标对象
     * @return 克隆对象
     */
    public static <T extends Serializable> T clone(final T obj) {
        if (obj != null) {
            final byte[] bytes = SerialUtils.encode(obj);
            if (!ArrayUtils.isEmpty(bytes)) {
                return (T) SerialUtils.decode(bytes);
            }
        }
        return obj;
    }

    /**
     * 序列化编码
     * <p/>
     *
     * @param object 原始对象
     * @return 字节数组
     * @author Wythe
     */
    public static byte[] encode(final Object object) {
        if (object != null) {
            try {
                return serializer.asByteArray(object);
            } catch (Throwable t) {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(t);
                }
            }
        }
        return null;
    }

    /**
     * 序列化解码
     * <p/>
     *
     * @param serial 字节数组
     * @return 原始对象
     * @author Wythe
     */
    public static Object decode(final byte[] serial) {
        if (!ArrayUtils.isEmpty(serial)) {
            try {
                return serializer.asObject(serial);
            } catch (Throwable t) {
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(t);
                }
            }
        }
        return null;
    }

    /**
     * 序列化解码
     * <p/>
     *
     * @param serial 字节数组
     * @param future 期望结果
     * @return 期望对象
     * @author Wythe
     */
    @SuppressWarnings("unchecked")
    public static <T> T decode(final byte[] serial, final Class<T> future) {
        if (!ArrayUtils.isEmpty(serial) && future != null) {
            Object object;
            try {
                object = serializer.asObject(serial);
            } catch (Throwable t) {
                object = null;
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(t);
                }
            }
            if (object != null && future.isInstance(object)) {
                return (T) object;
            }
        }
        return null;
    }

}
