/**
 * Created on 2016/2/26
 */
package org.sprout.core.assist;

import java.lang.reflect.Field;

/**
 * 反射工具类
 * <p/>
 *
 * @author Wythe
 */
public final class ReflectUtils {

    /**
     * 反射获得字段
     * <p/>
     *
     * @param clazz 反射类
     * @param field 字段名
     * @return 类字段
     * @author Wythe
     */
    public static Field findField(final Class<?> clazz, final String field) {
        return ReflectUtils.findField(clazz, field, null, true);
    }

    /**
     * 反射获得字段
     * <p/>
     *
     * @param clazz 反射类
     * @param field 字段名
     * @param these 是否反射相关父类
     * @return 类字段
     * @author Wythe
     */
    public static Field findField(final Class<?> clazz, final String field, final boolean these) {
        return ReflectUtils.findField(clazz, field, null, these);
    }

    /**
     * 反射获得字段
     * <p/>
     *
     * @param clazz 反射类
     * @param field 字段名
     * @param check 字段类型
     * @return 类字段
     * @author Wythe
     */
    public static Field findField(final Class<?> clazz, final String field, final Class<?> check) {
        return ReflectUtils.findField(clazz, field, check, true);
    }

    /**
     * 反射获得字段
     * <p/>
     *
     * @param clazz 反射类
     * @param field 字段名
     * @param check 字段类型
     * @param these 是否仅反射本类（不反射相关父类）
     * @return 类字段
     * @author Wythe
     */
    public static Field findField(final Class<?> clazz, final String field, final Class<?> check, final boolean these) {
        if (clazz == null || StringUtils.isEmpty(field)) {
            return null;
        }
        if (these) {
            try {
                Field f = clazz.getDeclaredField(field);
                if (f != null && (check == null || check.equals(f.getType()))) {
                    return f;
                }
            } catch (NoSuchFieldException e) {
                return null;
            }
            return null;
        }
        for (Class<?> c = clazz; c != Object.class && c != null; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            for (Field f : fields) {
                if (field.equals(f.getName()) && (check == null || check.equals(f.getType()))) {
                    return f;
                }
            }
        }
        return null;
    }

    /**
     * 反射静态字段取值
     * <p/>
     *
     * @param field 反射静态字段
     * @return 静态字段取值
     * @author Wythe
     */
    public static Object getField(final Field field) {
        return ReflectUtils.getField(null, field);
    }

    /**
     * 反射字段取值
     * <p/>
     *
     * @param owner 实例对象
     * @param field 反射字段
     * @return 字段取值
     * @author Wythe
     */
    public static Object getField(final Object owner, final Field field) {
        if (field != null) {
            final boolean access = field.isAccessible();
            if (!access) {
                field.setAccessible(true);
            }
            try {
                return field.get(owner);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return null;
            } finally {
                if (!access) {
                    field.setAccessible(access);
                }
            }
        }
        return null;
    }

    /**
     * 反射字段取值（按类型）
     * <p/>
     *
     * @param owner 实例对象
     * @param field 反射字段
     * @param clazz 字段类型
     * @return 字段取值
     * @author Wythe
     */
    @SuppressWarnings("unchecked")
    public static <T> T getField(final Object owner, final Field field, final Class<T> clazz) {
        final Object value = ReflectUtils.getField(owner, field);
        if (clazz != null && clazz.isInstance(value)) {
            return (T) value;
        }
        return null;
    }

    /**
     * 反射字段赋值
     * <p/>
     *
     * @param owner 实例对象
     * @param field 反射字段
     * @param value 字段赋值
     * @return 是否成功
     * @author Wythe
     */
    public static boolean setField(final Object owner, final Field field, final Object value) {
        if (owner != null && field != null) {
            final boolean access = field.isAccessible();
            if (!access) {
                field.setAccessible(true);
            }
            try {
                field.set(owner, value);
            } catch (IllegalArgumentException | IllegalAccessException e) {
                return false;
            } finally {
                if (!access) {
                    field.setAccessible(access);
                }
            }
            return true;
        }
        return false;
    }

}