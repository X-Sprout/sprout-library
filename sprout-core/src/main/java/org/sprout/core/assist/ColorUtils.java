/**
 * Created on 2016/2/25
 */
package org.sprout.core.assist;

import android.graphics.Color;

import java.util.Random;

/**
 * 颜色工具类
 * <p/>
 *
 * @author Wythe
 */
public final class ColorUtils {

    /**
     * 获得随机颜色
     *
     * @param min 最小范围
     * @param max 最大范围
     * @return 随机颜色
     */
    public static int getRandomColor(final int min, final int max) {
        return ColorUtils.getRandomColor(new Random(), min, max);
    }

    /**
     * 获得随机颜色
     *
     * @param rdm 随机对象
     * @param min 最小范围
     * @param max 最大范围
     * @return 随机颜色
     */
    public static int getRandomColor(final Random rdm, final int min, final int max) {
        // 设置范围
        int mini = Math.abs(min) > 255 ? 255 : Math.abs(min);
        int maxi = Math.abs(max) > 255 ? 255 : Math.abs(max);
        // 随机颜色
        return Color.rgb(
                min + rdm.nextInt(maxi - mini),
                min + rdm.nextInt(maxi - mini),
                min + rdm.nextInt(maxi - mini)
        );
    }

}
