/**
 * Created on 2016/2/25
 */
package org.sprout.core.assist;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 泛型工具类
 * <p/>
 *
 * @author Wythe
 */
public final class GenericUtils {

    /**
     * 获得泛型类型实际泛型类型
     * <p/>
     *
     * @param type 泛型类型
     * @return 类型数组
     * @author Wythe
     */
    public static Type[] getGenericTypes(final Type type) {
        return type instanceof ParameterizedType ? ((ParameterizedType) type).getActualTypeArguments() : new Type[0];
    }

    /**
     * 获得泛型类实际泛型类型
     * <p/>
     *
     * @param clazz 泛型类
     * @return 类型数组
     * @author Wythe
     */
    public static Type[] getGenericTypes(final Class<?> clazz) {
        final List<Type> result = new ArrayList<>();
        if (clazz != null) {
            final Type generic = clazz.getGenericSuperclass();
            if (generic instanceof ParameterizedType) {
                result.addAll(Arrays.asList(((ParameterizedType) generic).getActualTypeArguments()));
            }
            final Type[] generics = clazz.getGenericInterfaces();
            if (generics != null) {
                for (final Type type : generics) {
                    if (type instanceof ParameterizedType) {
                        result.addAll(Arrays.asList(((ParameterizedType) type).getActualTypeArguments()));
                    }
                }
            }
        }
        return result.toArray(new Type[result.size()]);
    }

}