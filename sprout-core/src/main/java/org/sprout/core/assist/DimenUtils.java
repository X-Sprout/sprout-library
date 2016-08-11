/**
 * Created on 2016/2/25
 */
package org.sprout.core.assist;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;

import org.sprout.SproutLib;

/**
 * 尺寸工具类
 * <p>
 *
 * @author Wythe
 */
public final class DimenUtils {

    /**
     * DP尺寸转换像素尺寸
     *
     * @param dp DP尺寸
     * @return 像素尺寸
     * @author Wythe
     */
    public static int dp2Px(final float dp) {
        if (dp > 0) {
            final Context context = SproutLib.getContext();
            if (context != null) {
                final Resources resources = context.getResources();
                if (resources != null) {
                    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
                }
            }
            throw new UnsupportedOperationException("Unsupported \'dp2Px\' convert");
        }
        return (int) dp;
    }

    /**
     * SP尺寸转换像素尺寸
     *
     * @param sp SP尺寸
     * @return 像素尺寸
     * @author Wythe
     */
    public static int sp2Px(final float sp) {
        if (sp > 0) {
            final Context context = SproutLib.getContext();
            if (context != null) {
                final Resources resources = context.getResources();
                if (resources != null) {
                    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, resources.getDisplayMetrics());
                }
            }
            throw new UnsupportedOperationException("Unsupported \'sp2Px\' convert");
        }
        return (int) sp;
    }

    /**
     * 像素尺寸转换DP尺寸
     *
     * @param px 像素尺寸
     * @return DP尺寸
     * @author Wythe
     */
    public static int px2Dp(final float px) {
        if (px > 0) {
            final Context context = SproutLib.getContext();
            if (context != null) {
                final Resources resources = context.getResources();
                if (resources != null) {
                    return (int) (px / resources.getDisplayMetrics().density + 0.5f);
                }
            }
            throw new UnsupportedOperationException("Unsupported \'px2Dp\' convert");
        }
        return (int) px;
    }

    /**
     * 像素尺寸转换SP尺寸
     *
     * @param px 像素尺寸
     * @return SP尺寸
     * @author Wythe
     */
    public static int px2Sp(final float px) {
        if (px > 0) {
            final Context context = SproutLib.getContext();
            if (context != null) {
                final Resources resources = context.getResources();
                if (resources != null) {
                    return (int) (px / resources.getDisplayMetrics().scaledDensity + 0.5f);
                }
            }
            throw new UnsupportedOperationException("Unsupported \'px2Sp\' convert");
        }
        return (int) px;
    }

}
