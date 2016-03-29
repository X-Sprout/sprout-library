/**
 * Created on 2016/2/26
 */
package org.sprout;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * Sprout库类
 * <p/>
 *
 * @author Wythe
 */
public final class SproutLib {

    /**
     * 框架名称
     */
    public final static String name = "sprout-lib";

    // 应用环境
    private static Context APP_CTX;

    // 应用资源
    private static Resources APP_RES;

    /**
     * 初始化框架
     *
     * @param context 应用环境
     * @author Wythe
     */
    public static void init(final Context context) {
        if (context == null) {
            throw new IllegalArgumentException("The application context cannot be null.");
        }
        // 初始环境
        SproutLib.APP_CTX = context.getApplicationContext();
        if (SproutLib.APP_CTX != null) {
            SproutLib.APP_RES = SproutLib.APP_CTX.getResources();
        }
    }

    /**
     * 获得应用环境
     *
     * @return 应用环境
     * @author Wythe
     */
    public static Context getContext() {
        if (SproutLib.APP_CTX == null) {
            throw new NullPointerException("The application context error.");
        }
        return SproutLib.APP_CTX;
    }

    /**
     * 获得应用资源
     *
     * @return 应用资源
     * @author Wythe
     */
    public static Resources getResource() {
        if (SproutLib.APP_RES == null) {
            SproutLib.APP_RES = SproutLib.getContext().getResources();
            if (SproutLib.APP_RES == null) {
                throw new NullPointerException("The application resource error.");
            }
        }
        return SproutLib.APP_RES;
    }

    /**
     * 获得颜色资源
     *
     * @param res 资源标识
     * @return 颜色资源
     * @author Wythe
     */
    public static int getColor(final int res) {
        try {
            return ContextCompat.getColor(SproutLib.getContext(), res);
        } catch (Resources.NotFoundException e) {
            return Color.TRANSPARENT;
        }
    }

    /**
     * 获得绘制资源
     *
     * @param res 资源标识
     * @return 绘制资源
     * @author Wythe
     */
    public static Drawable getDrawable(final int res) {
        try {
            return ContextCompat.getDrawable(SproutLib.getContext(), res);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

    /**
     * 获得字符串资源
     *
     * @param res 资源标识
     * @return 字符串资源
     * @author Wythe
     */
    public static String getString(final int res) {
        try {
            return SproutLib.getResource().getString(res);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

    /**
     * 获得字符串资源
     *
     * @param res 资源标识
     * @param fmt 填充数据
     * @return 字符串资源
     * @author Wythe
     */
    public static String getString(final int res, final Object... fmt) {
        try {
            return SproutLib.getResource().getString(res, fmt);
        } catch (Resources.NotFoundException e) {
            return null;
        }
    }

}
