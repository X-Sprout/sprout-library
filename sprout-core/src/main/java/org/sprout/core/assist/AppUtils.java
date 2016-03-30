/**
 * Created on 2016/2/25
 */
package org.sprout.core.assist;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PowerManager;

import org.sprout.SproutLib;

import java.util.List;

/**
 * 应用工具类
 * <p/>
 *
 * @author Wythe
 */
public final class AppUtils {

    /**
     * 获得应用名称
     *
     * @return 应用名称
     * @author Wythe
     */
    public static String getName() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                PackageInfo packageInfo;
                try {
                    packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    packageInfo = null;
                }
                if (packageInfo != null) {
                    return ObjectUtils.toString(packageInfo.applicationInfo.loadLabel(packageManager));
                }
            }

        }
        return StringUtils.EMPTY;
    }

    /**
     * 获得APK目录
     *
     * @return APK目录
     * @author Wythe
     */
    public static String getApkDir() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo != null) {
                return applicationInfo.sourceDir;
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获得JNI目录
     *
     * @return JNI目录
     * @author Wythe
     */
    public static String getJniDir() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo != null) {
                return applicationInfo.nativeLibraryDir;
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获得数据目录
     *
     * @return 数据目录
     * @author Wythe
     */
    public static String getDataDir() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo != null) {
                return applicationInfo.dataDir;
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获得应用类名称
     *
     * @return 应用类名称
     * @author Wythe
     */
    public static String getClassName() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo != null) {
                return applicationInfo.className;
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获得应用版本编码
     *
     * @return 应用版本编码
     * @author Wythe
     */
    public static int getVersionCode() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                PackageInfo packageInfo;
                try {
                    packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    packageInfo = null;
                }
                if (packageInfo != null) {
                    return packageInfo.versionCode;
                }
            }
        }
        return 0;
    }

    /**
     * 获得应用版本名称
     *
     * @return 应用版本名称
     * @author Wythe
     */
    public static String getVersionName() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final PackageManager packageManager = context.getPackageManager();
            if (packageManager != null) {
                PackageInfo packageInfo;
                try {
                    packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    packageInfo = null;
                }
                if (packageInfo != null) {
                    return packageInfo.versionName;
                }
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获得应用包名称
     *
     * @return 应用包名称
     * @author Wythe
     */
    public static String getPackageName() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            return context.getPackageName();
        }
        return StringUtils.EMPTY;
    }

    /**
     * 获得应用进程名称
     *
     * @return 应用进程名称
     * @author Wythe
     */
    public static String getProcessName() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final ApplicationInfo applicationInfo = context.getApplicationInfo();
            if (applicationInfo != null) {
                return applicationInfo.processName;
            }
        }
        return StringUtils.EMPTY;
    }

    /**
     * 屏幕是否点亮
     *
     * @return 屏幕是否点亮
     * @author Wythe
     */
    public static boolean isScreenon() {
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
     * 是否前台运行
     *
     * @return 是否在前台运行
     * @author Wythe
     */
    public static boolean isForeground() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (activityManager != null) {
                final List<ActivityManager.RunningAppProcessInfo> appProcesses = activityManager.getRunningAppProcesses();
                if (appProcesses != null && appProcesses.size() > 0) {
                    for (final ActivityManager.RunningAppProcessInfo appProcess : appProcesses) {
                        if (appProcess != null && appProcess.processName.equals(context.getPackageName())) {
                            return ((ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND == appProcess.importance) && AppUtils.isScreenon());
                        }
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检测应用是否安装
     *
     * @param packet 应用包名
     * @return 是否安装
     * @author Wythe
     */
    public static boolean isInstalled(final String packet) {
        final Context context = SproutLib.getContext();
        if (context == null) {
            return false;
        }
        final PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        try {
            packageManager.getPackageInfo(packet, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * 检测服务是否运行
     *
     * @param clazz 服务类
     * @return 是否运行
     * @author Wythe
     */
    public static boolean isServiceRunning(final Class clazz) {
        return clazz != null && AppUtils.isServiceRunning(clazz.getName());
    }

    /**
     * 检测服务是否运行
     *
     * @param className 服务名
     * @return 是否运行
     * @author Wythe
     */
    public static boolean isServiceRunning(final String className) {
        if (!StringUtils.isEmpty(className)) {
            final Context context = SproutLib.getContext();
            if (context != null) {
                final ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
                for (final ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
                    if (className.equals(service.service.getClassName())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
