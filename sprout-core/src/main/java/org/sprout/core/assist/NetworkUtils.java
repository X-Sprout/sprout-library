/**
 * Created on 2016/2/25
 */
package org.sprout.core.assist;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import org.sprout.SproutLib;

import java.lang.reflect.Method;

/**
 * 网络工具类
 * <p/>
 *
 * @author Wythe
 */
public final class NetworkUtils {

    private final static String GPRS_ENABLED = "mobile_data";
    private final static String GPRS_REFLECT = "setMobileDataEnabled";

    /**
     * 检测是否漫游
     *
     * @return 是否漫游
     * @author Wythe
     */
    public static boolean isRoaming() {
        final TelephonyManager handle = NetManager.Instance.getTelephony();
        if (handle == null) {
            return false;
        }
        return handle.isNetworkRoaming();
    }

    /**
     * 检测是否支持2G网络
     *
     * @return 是否支持
     * @author Wythe
     */
    public static boolean is2GSupport() {
        final TelephonyManager handle = NetManager.Instance.getTelephony();
        if (handle != null) {
            switch (handle.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                case TelephonyManager.NETWORK_TYPE_1xRTT: {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 检测是否支持3G网络
     *
     * @return 是否支持
     * @author Wythe
     */
    public static boolean is3GSupport() {
        final TelephonyManager handle = NetManager.Instance.getTelephony();
        if (handle != null) {
            switch (handle.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_EVDO_B: {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 检测是否支持4G网络
     *
     * @return 是否支持
     * @author Wythe
     */
    public static boolean is4GSupport() {
        final TelephonyManager handle = NetManager.Instance.getTelephony();
        if (handle != null) {
            switch (handle.getNetworkType()) {
                case TelephonyManager.NETWORK_TYPE_LTE: {
                    return true;
                }
                default: {
                    return false;
                }
            }
        }
        return false;
    }

    /**
     * 检测是否飞行模式
     *
     * @return 是否飞行模式
     * @author Wythe
     */
    public static boolean isFlightMode() {
        final Context context = SproutLib.getContext();
        if (context == null) {
            return false;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return Settings.Global.getInt(context.getContentResolver(), Settings.Global.AIRPLANE_MODE_ON, 0) == 1;
            } else {
                return Settings.System.getInt(context.getContentResolver(), Settings.System.AIRPLANE_MODE_ON, 0) == 1;
            }
        }
    }

    /**
     * 检测网络是否联网
     *
     * @param www 是否外网
     * @return 是否联网
     * @author Wythe
     */
    public static boolean isConnected(final boolean www) {
        final NetworkInfo netInfo = NetManager.Instance.getInfo();
        if (netInfo == null) {
            return false;
        }
        return netInfo.isConnected() && (www ? netInfo.isAvailable() : true);
    }

    /**
     * 检测是否WIFI网络联网
     *
     * @param www 是否外网
     * @return 是否WIFI网络联网
     * @author Wythe
     */
    public static boolean isWifiConnected(final boolean www) {
        final NetworkInfo netInfo = NetManager.Instance.getInfo();
        if (netInfo == null || ConnectivityManager.TYPE_WIFI != netInfo.getType()) {
            return false;
        }
        return netInfo.isConnected() && (www ? netInfo.isAvailable() : true);
    }

    /**
     * 检测是否GPRS网络联网
     *
     * @param www 是否外网
     * @return 是否GPRS网络联网
     * @author Wythe
     */
    public static boolean isGprsConnected(final boolean www) {
        final NetworkInfo netInfo = NetManager.Instance.getInfo();
        if (netInfo == null || ConnectivityManager.TYPE_MOBILE != netInfo.getType()) {
            return false;
        }
        return netInfo.isConnected() && (www ? netInfo.isAvailable() : true);
    }

    /**
     * 检测是否Ethernet网络联网
     *
     * @param www 是否外网
     * @return 是否Ethernet网络联网
     * @author Wythe
     */
    public static boolean isEthernetConnected(final boolean www) {
        final NetworkInfo netInfo = NetManager.Instance.getInfo();
        if (netInfo == null || ConnectivityManager.TYPE_ETHERNET != netInfo.getType()) {
            return false;
        }
        return netInfo.isConnected() && (www ? netInfo.isAvailable() : true);
    }

    /**
     * 检测WIFI是否开启
     *
     * @return 是否开启
     * @author Wythe
     */
    public static boolean isWifiEnable() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                return WifiManager.WIFI_STATE_DISABLED != wifiManager.getWifiState();
            }
        }
        return false;
    }

    /**
     * 检测GPRS是否开启
     *
     * @return 是否开启
     * @author Wythe
     */
    public static boolean isGprsEnable() {
        final Context context = SproutLib.getContext();
        if (context == null) {
            return false;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                return Settings.Global.getInt(context.getContentResolver(), GPRS_ENABLED, 0) == 1;
            } else {
                return Settings.Secure.getInt(context.getContentResolver(), GPRS_ENABLED, 0) == 1;
            }
        }
    }

    /**
     * 设置WIFI设备开关
     *
     * @param run 是否运行
     * @return 是否成功
     * @author Wythe
     */
    public static boolean setWifiDevice(final boolean run) {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            if (wifiManager != null) {
                return wifiManager.setWifiEnabled(run);
            }
        }
        return false;
    }

    /**
     * 设置GPRS设备开关
     *
     * @param run 是否运行
     * @return 是否成功
     * @author Wythe
     */
    public static boolean setGprsDevice(final boolean run) {
        final ConnectivityManager handle = NetManager.Instance.getConnectivity();
        if (handle != null) {
            Method method = null;
            try {
                method = handle.getClass().getDeclaredMethod(GPRS_REFLECT, boolean.class);
            } catch (NoSuchMethodException e) {
                method = null;
            } finally {
                if (method != null) {
                    method.setAccessible(true);
                    try {
                        method.invoke(handle, run);
                    } catch (Exception e) {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    private enum NetManager {
        Instance;

        final NetworkInfo getInfo() {
            final ConnectivityManager handle = this.getConnectivity();
            if (handle != null) {
                return handle.getActiveNetworkInfo();
            }
            return null;
        }

        final TelephonyManager getTelephony() {
            final Context context = SproutLib.getContext();
            if (context == null) {
                return null;
            }
            return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }

        final ConnectivityManager getConnectivity() {
            final Context context = SproutLib.getContext();
            if (context == null) {
                return null;
            }
            return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
    }

}
