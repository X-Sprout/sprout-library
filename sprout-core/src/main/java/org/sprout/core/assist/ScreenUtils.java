/**
 * Created on 2016/8/11
 */
package org.sprout.core.assist;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.PowerManager;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import org.sprout.SproutLib;
import org.sprout.bale.structure.RSize;

/**
 * 屏幕工具类
 * <p>
 *
 * @author Wythe
 */
public final class ScreenUtils {

    private final static String IDENT_TYPE = "dimen";
    private final static String IDENT_PACK = "android";
    private final static String IDENT_NAME = "status_bar_height";

    /**
     * 获得屏幕大小
     *
     * @return 屏幕大小
     * @author Wythe
     */
    public static RSize getScreenSize() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            if (windowManager != null) {
                final Display display = windowManager.getDefaultDisplay();
                if (display != null) {
                    final DisplayMetrics metrics = new DisplayMetrics();
                    display.getMetrics(metrics);
                    return new RSize(metrics.widthPixels, metrics.heightPixels);
                }
            }
        }
        return null;
    }

    /**
     * 屏幕是否点亮
     *
     * @return 是否点亮
     * @author Wythe
     */
    public static boolean isOpenScreen() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final PowerManager powerManager = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            if (powerManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
                    return powerManager.isInteractive();
                } else {
                    return powerManager.isScreenOn();
                }
            }
        }
        return false;
    }

    /**
     * 屏幕是否锁屏
     *
     * @return 屏幕是否锁屏
     * @author Wythe
     */
    public static boolean isLockScreen() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
            if (keyguardManager != null) {
                return keyguardManager.inKeyguardRestrictedInputMode();
            }
        }
        return false;
    }

    /**
     * 获得状态栏高度
     *
     * @return 状态栏高度
     * @author Wythe
     */
    public static int getStatusBarHeight() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final Resources resources = context.getResources();
            if (resources != null) {
                final int resId = resources.getIdentifier(IDENT_NAME, IDENT_TYPE, IDENT_PACK);
                if (resId > 0) {
                    return resources.getDimensionPixelSize(resId);
                }
            }

        }
        return 0;
    }

    /**
     * 获得操作栏高度
     *
     * @return 操作栏高度
     * @author Wythe
     */
    public static int getActionBarHeight(final Activity activity) {
        if (activity != null) {
            final Resources res = activity.getResources();
            if (res != null) {
                final Resources.Theme theme = activity.getTheme();
                if (theme != null) {
                    final TypedValue tv = new TypedValue();
                    if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                        return TypedValue.complexToDimensionPixelSize(tv.data, res.getDisplayMetrics());
                    }
                }
            }
        }
        return 0;
    }

}
