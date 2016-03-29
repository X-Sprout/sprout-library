/**
 * Created on 2016/3/3
 */
package org.sprout.core.assist;

import java.math.BigDecimal;

/**
 * 运算工具类
 * <p/>
 *
 * @author Wythe
 */
public final class ArithUtils {

    /**
     * 高精度格式化
     *
     * @param digit 双精度数
     * @return 格式化数
     * @author Wythe
     */
    public static double format(final double digit) {
        return ArithUtils.format(digit, 10);
    }

    /**
     * 高精度格式化
     *
     * @param digit 双精度数
     * @param scale 格式化精度
     * @return 格式化数
     * @author Wythe
     */
    public static double format(final double digit, final int scale) {
        BigDecimal decimal;
        try {
            decimal = BigDecimal.valueOf(digit);
        } catch (NumberFormatException e) {
            decimal = null;
        }
        return decimal != null ? decimal.divide(new BigDecimal(1), scale, BigDecimal.ROUND_HALF_UP).doubleValue() : digit;
    }

}
