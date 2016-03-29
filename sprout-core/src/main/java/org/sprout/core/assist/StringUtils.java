/**
 * Created on 2016/2/26
 */
package org.sprout.core.assist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 字符串工具类
 * <p/>
 *
 * @author Wythe
 */
public final class StringUtils {

    /**
     * 空字符串常量
     */
    public final static String EMPTY = "";
    /**
     * 空白字符串常量
     */
    public final static String BLANK = " ";
    /**
     * 逗号字符串常量
     */
    public final static String COMMA = ",";
    /**
     * 单引号字符串常量
     */
    public final static String QUOTE = "\'";
    /**
     * 斜杠字符串常量
     */
    public final static String SLASH = "/";
    /**
     * 双引号字符串常量
     */
    public final static String DQUOTE = "\"";
    /**
     * 反斜杠常量
     */
    public final static String BSLASH = "\\";


    /**
     * 判断是否空字符串
     * <p/>
     *
     * @param cs 字符串
     * @return 是否空字符串
     * @author 吴颖
     */
    public static boolean isEmpty(final CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    /**
     * 判断是否存在空字符串
     * <p/>
     *
     * @param css 字符串（不定参）
     * @return 是否存在空
     * @author 吴颖
     */
    public static boolean isAnyEmpty(final CharSequence... css) {
        if (ArrayUtils.isEmpty(css)) {
            return true;
        }
        for (final CharSequence cs : css) {
            if (StringUtils.isEmpty(cs)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 生成字符串
     *
     * @param css 字符串
     * @return 字符串
     * @author 吴颖
     */
    public static String toString(final CharSequence css) {
        return StringUtils.toString(css, "");
    }

    /**
     * 生成字符串
     *
     * @param css 字符串
     * @param def 默认值
     * @return 字符串
     * @author 吴颖
     */
    public static String toString(final CharSequence css, final CharSequence def) {
        return !StringUtils.isEmpty(css) ? css.toString() : (
                def != null ? def.toString() : null
        );
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(T...)
     */
    @SafeVarargs
    public static <T> String join(final T... elements) {
        return StringUtils.join(elements, null);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(int[], char)
     */
    public static String join(final int[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(int[], char, int, int)
     */
    public static String join(final int[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return StringUtils.EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(int[], char)
     */
    public static String join(final byte[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(byte[], char, int, int)
     */
    public static String join(final byte[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return StringUtils.EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(char[], char)
     */
    public static String join(final char[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(char[], char, int, int)
     */
    public static String join(final char[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return StringUtils.EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(long[], char)
     */
    public static String join(final long[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(long[], char, int, int)
     */
    public static String join(final long[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return StringUtils.EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(short[], char)
     */
    public static String join(final short[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(short[], char, int, int)
     */
    public static String join(final short[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return StringUtils.EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(float[], char)
     */
    public static String join(final float[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(float[], char, int, int)
     */
    public static String join(final float[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return StringUtils.EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(double[], char)
     */
    public static String join(final double[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(double[], char, int, int)
     */
    public static String join(final double[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return StringUtils.EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            buf.append(array[i]);
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(java.lang.Object[], char)
     */
    public static String join(final Object[] array, final char separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(java.lang.Object[], char, int, int)
     */
    public static String join(final Object[] array, final char separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return StringUtils.EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        for (int i = startIndex; i < endIndex; i++) {
            if (i > startIndex) {
                buf.append(separator);
            }
            if (array[i] != null) {
                buf.append(array[i]);
            }
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(java.lang.Object[], java.lang.String)
     */
    public static String join(final Object[] array, final String separator) {
        if (array == null) {
            return null;
        }
        return StringUtils.join(array, separator, 0, array.length);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(java.lang.Object[], java.lang.String, int, int)
     */
    public static String join(final Object[] array, final String separator, final int startIndex, final int endIndex) {
        if (array == null) {
            return null;
        }
        final int noOfItems = endIndex - startIndex;
        if (noOfItems <= 0) {
            return StringUtils.EMPTY;
        }
        final StringBuilder buf = new StringBuilder(noOfItems * 16);
        if (separator == null) {
            for (int i = startIndex; i < endIndex; i++) {
                if (i > startIndex) {
                    buf.append(StringUtils.EMPTY);
                }
                if (array[i] != null) {
                    buf.append(array[i]);
                }
            }
        } else {
            for (int i = startIndex; i < endIndex; i++) {
                if (i > startIndex) {
                    buf.append(separator);
                }
                if (array[i] != null) {
                    buf.append(array[i]);
                }
            }
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(java.lang.Iterable, char)
     */
    public static String join(final Iterable<?> iterable, final char separator) {
        if (iterable == null) {
            return null;
        }
        return StringUtils.join(iterable.iterator(), separator);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(java.util.Iterator, char)
     */
    public static String join(final Iterator<?> iterator, final char separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return StringUtils.EMPTY;
        }
        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(first);
        }
        final StringBuilder buf = new StringBuilder(256);
        if (first != null) {
            buf.append(first);
        }
        while (iterator.hasNext()) {
            buf.append(separator);
            final Object obj = iterator.next();
            if (obj != null) {
                buf.append(obj);
            }
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(java.lang.Iterable, java.lang.String)
     */
    public static String join(final Iterable<?> iterable, final String separator) {
        if (iterable == null) {
            return null;
        }
        return StringUtils.join(iterable.iterator(), separator);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#join(java.util.Iterator, java.lang.String)
     */
    public static String join(final Iterator<?> iterator, final String separator) {
        if (iterator == null) {
            return null;
        }
        if (!iterator.hasNext()) {
            return StringUtils.EMPTY;
        }
        final Object first = iterator.next();
        if (!iterator.hasNext()) {
            return ObjectUtils.toString(first);
        }
        final StringBuilder buf = new StringBuilder(256);
        if (first != null) {
            buf.append(first);
        }
        if (separator != null) {
            while (iterator.hasNext()) {
                buf.append(separator);
                final Object obj = iterator.next();
                if (obj != null) {
                    buf.append(obj);
                }
            }
        } else {
            while (iterator.hasNext()) {
                final Object obj = iterator.next();
                if (obj != null) {
                    buf.append(obj);
                }
            }
        }
        return buf.toString();
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#split(java.lang.String)
     */
    public static String[] split(final String str) {
        return StringUtils.split(str, null, -1);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#split(java.lang.String, char)
     */
    public static String[] split(final String str, final char separatorChar) {
        return StringUtils.splitWorker(str, separatorChar, false);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#split(java.lang.String, java.lang.String)
     */
    public static String[] split(final String str, final String separatorChars) {
        return StringUtils.splitWorker(str, separatorChars, -1, false);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#split(java.lang.String, java.lang.String, int)
     */
    public static String[] split(final String str, final String separatorChars, final int max) {
        return StringUtils.splitWorker(str, separatorChars, max, false);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#split(java.lang.String, char, boolean)
     */
    private static String[] splitWorker(final String str, final char separatorChar, final boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (str.length() == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        int i = 0, s = 0;
        boolean msee = false, lsee = false;
        final List<String> list = new ArrayList<>();
        while (i < len) {
            if (str.charAt(i) == separatorChar) {
                if (msee || preserveAllTokens) {
                    list.add(str.substring(s, i));
                    msee = false;
                    lsee = true;
                }
                s = ++i;
                continue;
            }
            lsee = false;
            msee = true;
            i++;
        }
        if (msee || preserveAllTokens && lsee) {
            list.add(str.substring(s, i));
        }
        return list.toArray(new String[list.size()]);
    }

    /**
     * @see org.apache.commons.lang3 -> StringUtils#split(java.lang.String, java.lang.String, int, int)
     */
    private static String[] splitWorker(final String str, final String separatorChars, final int max, final boolean preserveAllTokens) {
        if (str == null) {
            return null;
        }
        final int len = str.length();
        if (len == 0) {
            return ArrayUtils.EMPTY_STRING_ARRAY;
        }
        int i = 0, s = 0, p = 1;
        boolean msee = false, lsee = false;
        final List<String> list = new ArrayList<>();
        if (separatorChars == null) {
            while (i < len) {
                if (Character.isWhitespace(str.charAt(i))) {
                    if (msee || preserveAllTokens) {
                        lsee = true;
                        if (p++ == max) {
                            i = len;
                            lsee = false;
                        }
                        list.add(str.substring(s, i));
                        msee = false;
                    }
                    s = ++i;
                    continue;
                }
                lsee = false;
                msee = true;
                i++;
            }
        } else if (separatorChars.length() == 1) {
            final char sep = separatorChars.charAt(0);
            while (i < len) {
                if (str.charAt(i) == sep) {
                    if (msee || preserveAllTokens) {
                        lsee = true;
                        if (p++ == max) {
                            i = len;
                            lsee = false;
                        }
                        list.add(str.substring(s, i));
                        msee = false;
                    }
                    s = ++i;
                    continue;
                }
                lsee = false;
                msee = true;
                i++;
            }
        } else {
            while (i < len) {
                if (separatorChars.indexOf(str.charAt(i)) >= 0) {
                    if (msee || preserveAllTokens) {
                        lsee = true;
                        if (p++ == max) {
                            i = len;
                            lsee = false;
                        }
                        list.add(str.substring(s, i));
                        msee = false;
                    }
                    s = ++i;
                    continue;
                }
                lsee = false;
                msee = true;
                i++;
            }
        }
        if (msee || preserveAllTokens && lsee) {
            list.add(str.substring(s, i));
        }
        return list.toArray(new String[list.size()]);
    }

}
