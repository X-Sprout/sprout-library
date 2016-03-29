/**
 * Created on 2016/3/3
 */
package org.sprout.core.assist;

/**
 * 异常工具类
 * <p/>
 *
 * @author Wythe
 */
public final class ErrorUtils {

    /**
     * 获得异常原因
     *
     * @param err 异常
     * @return 异常原因
     * @author Wythe
     */
    public static String getCause(final Throwable err) {
        if (err != null) {
            final Throwable src = err.getCause();
            if (src != null) {
                return StringUtils.toString(src.getMessage(), src.toString());
            } else {
                return StringUtils.toString(err.getMessage(), err.toString());
            }
        }
        return StringUtils.EMPTY;
    }

}
