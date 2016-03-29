/**
 * Created on 2016/2/26
 */
package org.sprout.core.assist;

import org.sprout.SproutLib;
import org.sprout.core.logging.Lc;

import java.lang.reflect.Array;

/**
 * 对象工具包
 * <p/>
 *
 * @author Wythe
 */
public final class ObjectUtils {

    private final static String METHOD_CLONE = "clone";

    // INT默认值
    private static int DEFAULT_INT;
    // CHAR默认值
    private static char DEFAULT_CHAR;
    // BYTE默认值
    private static byte DEFAULT_BYTE;
    // LONG默认值
    private static long DEFAULT_LONG;
    // SHORT默认值
    private static short DEFAULT_SHORT;
    // FLOAT默认值
    private static float DEFAULT_FLOAT;
    // DOUBLE默认值
    private static double DEFAULT_DOUBLE;
    // BOOLEAN默认值
    private static boolean DEFAULT_BOOLEAN;

    /**
     * 获得整型默认值
     * <p/>
     *
     * @return 整型默认值
     * @author Wythe
     */
    public static int getDefaultInt() {
        return ObjectUtils.DEFAULT_INT;
    }

    /**
     * 获得字符默认值
     * <p/>
     *
     * @return 字符默认值
     * @author Wythe
     */
    public static char defaultChar() {
        return ObjectUtils.DEFAULT_CHAR;
    }

    /**
     * 获得字节默认值
     * <p/>
     *
     * @return 字节默认值
     * @author Wythe
     */
    public static byte defaultByte() {
        return ObjectUtils.DEFAULT_BYTE;
    }

    /**
     * 获得长整型默认值
     * <p/>
     *
     * @return 长整型默认值
     * @author Wythe
     */
    public static long defaultLong() {
        return ObjectUtils.DEFAULT_LONG;
    }

    /**
     * 获得短整型默认值
     * <p/>
     *
     * @return 短整型默认值
     * @author Wythe
     */
    public static short defaultShort() {
        return ObjectUtils.DEFAULT_SHORT;
    }

    /**
     * 获得单精度默认值
     * <p/>
     *
     * @return 单精度默认值
     * @author Wythe
     */
    public static float defaultFloat() {
        return ObjectUtils.DEFAULT_FLOAT;
    }

    /**
     * 获得双精度默认值
     * <p/>
     *
     * @return 双精度默认值
     * @author Wythe
     */
    public static double defaultDouble() {
        return ObjectUtils.DEFAULT_DOUBLE;
    }

    /**
     * 获得布尔型默认值
     * <p/>
     *
     * @return 布尔型默认值
     * @author Wythe
     */
    public static boolean defaultBoolean() {
        return ObjectUtils.DEFAULT_BOOLEAN;
    }

    /**
     * 对象转换为字符串
     * <p/>
     *
     * @param obj 对象
     * @return 字符串
     * @author Wythe
     */
    public static String toString(final Object obj) {
        return ObjectUtils.toString(obj, StringUtils.EMPTY);
    }

    /**
     * 对象转换为字符串
     * <p/>
     *
     * @param obj 对象
     * @param str 空对象替换值
     * @return 字符串
     * @author Wythe
     */
    public static String toString(final Object obj, final String str) {
        return obj == null ? str : StringUtils.EMPTY + obj;
    }

    /**
     * 克隆对象
     *
     * @param obj 目标对象
     * @return 克隆对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends Cloneable> T clone(final T obj) {
        Object result = null;
        if (obj.getClass().isArray()) {
            final Class<?> componentType = obj.getClass().getComponentType();
            if (!componentType.isPrimitive()) {
                result = ((Object[]) obj).clone();
            } else {
                int length = Array.getLength(obj);
                if (length > 0) {
                    result = Array.newInstance(componentType, length);
                    while (length-- > 0) {
                        Array.set(result, length, Array.get(obj, length));
                    }
                }
            }
        } else {
            try {
                result = Object.class.getDeclaredMethod(METHOD_CLONE).invoke(obj);
            } catch (Exception e) {
                result = null;
                if (Lc.E) {
                    Lc.t(SproutLib.name).e(e);
                }
            }
        }
        return result != null ? (T) result : null;
    }

}