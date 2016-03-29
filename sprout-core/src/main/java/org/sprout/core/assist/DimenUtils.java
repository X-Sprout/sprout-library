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
 * <p/>
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
        }
        return (int) dp;
    }

}
