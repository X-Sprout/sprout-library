/**
 * Created on 2016/2/26
 */
package org.sprout.core.assist;

/**
 * 数组工具类
 * <p/>
 *
 * @author Wythe
 */
public final class ArrayUtils {

    /**
     * 空类型数组常量
     */
    public final static Class<?>[] EMPTY_CLASS_ARRAY = new Class<?>[0];
    /**
     * 空整形数组常量
     */
    public final static int[] EMPTY_INT_ARRAY = new int[0];
    /**
     * 空整形对象数组常量
     */
    public final static Integer[] EMPTY_INTEGER_ARRAY = new Integer[0];
    /**
     * 空长整形数组常量
     */
    public final static long[] EMPTY_long_ARRAY = new long[0];
    /**
     * 空长整形对象数组常量
     */
    public final static Long[] EMPTY_LONG_ARRAY = new Long[0];
    /**
     * 空字符数组常量
     */
    public final static char[] EMPTY_char_ARRAY = new char[0];
    /**
     * 空字符对象数组常量
     */
    public final static Character[] EMPTY_CHAR_ARRAY = new Character[0];
    /**
     * 空字节数组常量
     */
    public final static byte[] EMPTY_byte_ARRAY = new byte[0];
    /**
     * 空字节对象数组常量
     */
    public final static Byte[] EMPTY_BYTE_ARRAY = new Byte[0];
    /**
     * 空短整型数组常量
     */
    public final static short[] EMPTY_short_ARRAY = new short[0];
    /**
     * 空短整型对象数组常量
     */
    public final static Short[] EMPTY_SHORT_ARRAY = new Short[0];
    /**
     * 空单精度浮点数组常量
     */
    public final static float[] EMPTY_float_ARRAY = new float[0];
    /**
     * 空单精度浮点对象数组常量
     */
    public final static Float[] EMPTY_FLOAT_ARRAY = new Float[0];
    /**
     * 空双精度浮点数组常量
     */
    public final static double[] EMPTY_double_ARRAY = new double[0];
    /**
     * 空双精度浮点对象数组常量
     */
    public final static Double[] EMPTY_DOUBLE_ARRAY = new Double[0];
    /**
     * 空字符串数组常量
     */
    public final static String[] EMPTY_STRING_ARRAY = new String[0];
    /**
     * 空对象数组常量
     */
    public final static Object[] EMPTY_OBJECT_ARRAY = new Object[0];
    /**
     * 空布尔数组常量
     */
    public final static boolean[] EMPTY_boolean_ARRAY = new boolean[0];
    /**
     * 空布尔对象数组常量
     */
    public final static Boolean[] EMPTY_BOOLEAN_ARRAY = new Boolean[0];


    /**
     * 整形数组求和
     * <p/>
     *
     * @param array 整形数组
     * @return 整形数组的和
     * @author Wythe
     */
    public static int sum(final int[] array) {
        if (array != null) {
            int sum = 0;
            for (final int i : array) {
                sum += i;
            }
            return sum;
        }
        return 0;
    }

    /**
     * 长整形数组求和
     * <p/>
     *
     * @param array 整形数组
     * @return 长整形数组的和
     * @author Wythe
     */
    public static long sum(final long[] array) {
        if (array != null) {
            long sum = 0;
            for (final long l : array) {
                sum += l;
            }
            return sum;
        }
        return 0;
    }

    /**
     * 短整形数据求和
     * <p/>
     *
     * @param array 短整形数组
     * @return 短整形数组的和
     * @author Wythe
     */
    public static short sum(final short[] array) {
        if (array != null) {
            short sum = 0;
            for (final short s : array) {
                sum += s;
            }
            return sum;
        }
        return 0;
    }

    /**
     * 单精度浮点数组求和
     * <p/>
     *
     * @param array 单精度浮点数组
     * @return 单精度浮点数组的和
     * @author Wythe
     */
    public static float sum(final float[] array) {
        if (array != null) {
            float sum = 0;
            for (final float f : array) {
                sum += f;
            }
            return sum;
        }
        return 0;
    }

    /**
     * 双精度浮点数组求和
     * <p/>
     *
     * @param array 双精度浮点数组
     * @return 双精度浮点数组的和
     * @author Wythe
     */
    public static double sum(final double[] array) {
        if (array != null) {
            double sum = 0;
            for (final double d : array) {
                sum += d;
            }
            return sum;
        }
        return 0;
    }

    /**
     * 是否空整形数组
     * <p/>
     *
     * @param array 整形数组
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final int[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否存在空整形数组
     * <p/>
     *
     * @param arrays 整形数组（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final int[]... arrays) {
        if (ArrayUtils.isEmpty(arrays)) {
            return true;
        }
        for (final int[] array : arrays) {
            if (ArrayUtils.isEmpty(array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否空字节数组
     * <p/>
     *
     * @param array 字节数组
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final byte[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否存在空字节数组
     * <p/>
     *
     * @param arrays 字节数组（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final byte[]... arrays) {
        if (ArrayUtils.isEmpty(arrays)) {
            return true;
        }
        for (final byte[] array : arrays) {
            if (ArrayUtils.isEmpty(array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否空字符数组
     * <p/>
     *
     * @param array 字符数组
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final char[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否存在空字符数组
     * <p/>
     *
     * @param arrays 字符数组（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final char[]... arrays) {
        if (ArrayUtils.isEmpty(arrays)) {
            return true;
        }
        for (final char[] array : arrays) {
            if (ArrayUtils.isEmpty(array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否空长整形数组
     * <p/>
     *
     * @param array 长整形数组
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final long[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否存在空长整形数组
     * <p/>
     *
     * @param arrays 长整形数组（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final long[]... arrays) {
        if (ArrayUtils.isEmpty(arrays)) {
            return true;
        }
        for (final long[] array : arrays) {
            if (ArrayUtils.isEmpty(array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否空短整形数组
     * <p/>
     *
     * @param array 短整形数组
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final short[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否存在空短整形数组
     * <p/>
     *
     * @param arrays 短整形数组（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final short[]... arrays) {
        if (ArrayUtils.isEmpty(arrays)) {
            return true;
        }
        for (final short[] array : arrays) {
            if (ArrayUtils.isEmpty(array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否空单精度浮点数组
     * <p/>
     *
     * @param array 单精度浮点数组
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final float[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否存在空单精度浮点数组
     * <p/>
     *
     * @param arrays 单精度浮点数组（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final float[]... arrays) {
        if (ArrayUtils.isEmpty(arrays)) {
            return true;
        }
        for (final float[] array : arrays) {
            if (ArrayUtils.isEmpty(array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否空双精度浮点数组
     * <p/>
     *
     * @param array 双精度浮点数组
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final double[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否存在空双精度浮点数组
     * <p/>
     *
     * @param arrays 双精度浮点数组（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final double[]... arrays) {
        if (ArrayUtils.isEmpty(arrays)) {
            return true;
        }
        for (final double[] array : arrays) {
            if (ArrayUtils.isEmpty(array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否空对象数组
     * <p/>
     *
     * @param array 对象数组
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final Object[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否存在空对象数组
     * <p/>
     *
     * @param arrays 对象数组（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final Object[]... arrays) {
        if (ArrayUtils.isEmpty(arrays)) {
            return true;
        }
        for (final Object[] array : arrays) {
            if (ArrayUtils.isEmpty(array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 是否空布尔数组
     * <p/>
     *
     * @param array 布尔数组
     * @return 是否为空
     * @author Wythe
     */
    public static boolean isEmpty(final boolean[] array) {
        return array == null || array.length == 0;
    }

    /**
     * 是否存在空布尔数组
     * <p/>
     *
     * @param arrays 布尔数组（不定参）
     * @return 是否存在空
     * @author Wythe
     */
    public static boolean isAnyEmpty(final boolean[]... arrays) {
        if (ArrayUtils.isEmpty(arrays)) {
            return true;
        }
        for (final boolean[] array : arrays) {
            if (ArrayUtils.isEmpty(array)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 字符数组转换为字符对象数组
     * <p/>
     *
     * @param array 字符数组
     * @return 字符对象数组
     * @author Wythe
     */
    public static Character[] toObject(final char[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final Character[] result = new Character[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = Character.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 字符对象数组转换为字符数组
     * <p/>
     *
     * @param array 字符对象数组
     * @return 字符数组
     * @author Wythe
     */
    public static char[] toPrimitive(final Character[] array) {
        return ArrayUtils.toPrimitive(array, (char) 0);
    }

    /**
     * 字符对象数组转换为字符数组
     * <p/>
     *
     * @param array 字符对象数组
     * @param value 空对象替换值
     * @return 字符数组
     * @author Wythe
     */
    public static char[] toPrimitive(final Character[] array, final char value) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_char_ARRAY;
        }
        final char[] result = new char[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = (array[i] == null ? value : array[i].charValue());
        }
        return result;
    }

    /**
     * 字节数组转换为字节对象数组
     * <p/>
     *
     * @param array 字节数组
     * @return 字节对象数组
     * @author Wythe
     */
    public static Byte[] toObject(final byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_BYTE_ARRAY;
        }
        final Byte[] result = new Byte[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = Byte.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 字节对象数组转换为字节数组
     * <p/>
     *
     * @param array 字节对象数组
     * @return 字节数组
     * @author Wythe
     */
    public static byte[] toPrimitive(final Byte[] array) {
        return ArrayUtils.toPrimitive(array, (byte) 0);
    }

    /**
     * 字节对象数组转换为字节数组
     * <p/>
     *
     * @param array 字节对象数组
     * @param value 空对象替换值
     * @return 字节数组
     * @author Wythe
     */
    public static byte[] toPrimitive(final Byte[] array, final byte value) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_byte_ARRAY;
        }
        final byte[] result = new byte[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = (array[i] == null ? value : array[i].byteValue());
        }
        return result;
    }

    /**
     * 整形数组转换为整形对象数组
     * <p/>
     *
     * @param array 整形数组
     * @return 整形对象数组
     * @author Wythe
     */
    public static Integer[] toObject(final int[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_INTEGER_ARRAY;
        }
        final Integer[] result = new Integer[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = Integer.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 整形对象数组转换为整形数组
     * <p/>
     *
     * @param array 整形对象数组
     * @return 整形数组
     * @author Wythe
     */
    public static int[] toPrimitive(final Integer[] array) {
        return ArrayUtils.toPrimitive(array, 0);
    }

    /**
     * 整形对象数组转换为整形数组
     * <p/>
     *
     * @param array 整形对象数组
     * @param value 空对象替换值
     * @return 整形数组
     * @author Wythe
     */
    public static int[] toPrimitive(final Integer[] array, final int value) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }
        final int[] result = new int[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = (array[i] == null ? value : array[i].intValue());
        }
        return result;
    }

    /**
     * 长整形数组转换为长整形对象数组
     * <p/>
     *
     * @param array 长整形数组
     * @return 长整形对象数组
     * @author Wythe
     */
    public static Long[] toObject(final long[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_LONG_ARRAY;
        }
        final Long[] result = new Long[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = Long.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 长整形对象数组转换为长整形数组
     * <p/>
     *
     * @param array 长整形对象数组
     * @return 长整形数组
     * @author Wythe
     */
    public static long[] toPrimitive(final Long[] array) {
        return ArrayUtils.toPrimitive(array, 0L);
    }

    /**
     * 长整形对象数组转换为长整形数组
     * <p/>
     *
     * @param array 长整形对象数组
     * @param value 空对象替换值
     * @return 长整形数组
     * @author Wythe
     */
    public static long[] toPrimitive(final Long[] array, final long value) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_long_ARRAY;
        }
        final long[] result = new long[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = (array[i] == null ? value : array[i].longValue());
        }
        return result;
    }

    /**
     * 短整型数组转换为短整形对象数组
     * <p/>
     *
     * @param array 短整型数组
     * @return 短整形对象数组
     * @author Wythe
     */
    public static Short[] toObject(final short[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_SHORT_ARRAY;
        }
        final Short[] result = new Short[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = Short.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 短整型对象数组转换为短整型数组
     * <p/>
     *
     * @param array 短整型对象数组
     * @return 短整型数组
     * @author Wythe
     */
    public static short[] toPrimitive(final Short[] array) {
        return ArrayUtils.toPrimitive(array, (short) 0);
    }

    /**
     * 短整型对象数组转换为短整型数组
     * <p/>
     *
     * @param array 短整型对象数组
     * @param value 空对象替换值
     * @return 短整型数组
     * @author Wythe
     */
    public static short[] toPrimitive(final Short[] array, final short value) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_short_ARRAY;
        }
        final short[] result = new short[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = (array[i] == null ? value : array[i].shortValue());
        }
        return result;
    }

    /**
     * 单精度浮点数组转换为单精度浮点数组对象
     * <p/>
     *
     * @param array 单精度浮点数组
     * @return 单精度浮点数组对象
     * @author Wythe
     */
    public static Float[] toObject(final float[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_FLOAT_ARRAY;
        }
        final Float[] result = new Float[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = Float.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 单精度浮点数组对象转换为单精度浮点数组
     * <p/>
     *
     * @param array 单精度浮点数组对象
     * @return 单精度浮点数组
     * @author Wythe
     */
    public static float[] toPrimitive(final Float[] array) {
        return ArrayUtils.toPrimitive(array, 0.0F);
    }

    /**
     * 单精度浮点数组对象转换为单精度浮点数组
     * <p/>
     *
     * @param array 单精度浮点数组对象
     * @param value 空对象替换值
     * @return 单精度浮点数组
     * @author Wythe
     */
    public static float[] toPrimitive(final Float[] array, final float value) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_float_ARRAY;
        }
        final float[] result = new float[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = (array[i] == null ? value : array[i].floatValue());
        }
        return result;
    }

    /**
     * 双精度浮点数组转换为双精度浮点对象数组
     * <p/>
     *
     * @param array 双精度浮点数组
     * @return 双精度浮点对象数组
     * @author Wythe
     */
    public static Double[] toObject(final double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_DOUBLE_ARRAY;
        }
        final Double[] result = new Double[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = Double.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 双精度浮点对象数组转换为双精度浮点数组
     * <p/>
     *
     * @param array 双精度浮点对象数组
     * @return 双精度浮点数组
     * @author Wythe
     */
    public static double[] toPrimitive(final Double[] array) {
        return ArrayUtils.toPrimitive(array, 0.0D);
    }

    /**
     * 双精度浮点对象数组转换为双精度浮点数组
     * <p/>
     *
     * @param array 双精度浮点对象数组
     * @param value 空对象替换值
     * @return 双精度浮点数组
     * @author Wythe
     */
    public static double[] toPrimitive(final Double[] array, final double value) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_double_ARRAY;
        }
        final double[] result = new double[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = (array[i] == null ? value : array[i].doubleValue());
        }
        return result;
    }

    /**
     * 布尔数组转换为布尔对象数组
     * <p/>
     *
     * @param array 布尔数组
     * @return 布尔对象数组
     * @author Wythe
     */
    public static Boolean[] toObject(final boolean[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_BOOLEAN_ARRAY;
        }
        final Boolean[] result = new Boolean[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = array[i] ? Boolean.TRUE : Boolean.FALSE;
        }
        return result;
    }

    /**
     * 布尔对象数组转换为布尔数组
     * <p/>
     *
     * @param array 布尔对象数组
     * @return 布尔数组
     * @author Wythe
     */
    public static boolean[] toPrimitive(final Boolean[] array) {
        return ArrayUtils.toPrimitive(array, false);
    }

    /**
     * 布尔对象数组转换为布尔数组
     * <p/>
     *
     * @param array 布尔对象数组
     * @param value 空对象替换值
     * @return 布尔数组
     * @author Wythe
     */
    public static boolean[] toPrimitive(final Boolean[] array, final boolean value) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return ArrayUtils.EMPTY_boolean_ARRAY;
        }
        final boolean[] result = new boolean[array.length];
        for (int i = 0, l = array.length; i < l; i++) {
            result[i] = (array[i] == null ? value : array[i].booleanValue());
        }
        return result;
    }

}