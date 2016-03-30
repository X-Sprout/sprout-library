/**
 * Created on 2016/2/25
 */
package org.sprout.core.assist;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import org.sprout.SproutLib;

/**
 * 设备工具类
 * <p/>
 *
 * @author Wythe
 */
public final class DevUtils {

    private final static String MOCK_SDK = "sdk";
    private final static String MOCK_GSDK = "google_sdk";
    private final static String MOCK_IMEI = "000000000000000";

    /**
     * 是否模拟设备
     *
     * @return 是否模拟
     * @author Wythe
     */
    public static boolean isEmulator() {
        final Context context = SproutLib.getContext();
        if (context != null) {
            final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (telephonyManager != null) {
                if (MOCK_IMEI.equals(telephonyManager.getDeviceId())) {
                    return true;
                }
            }
            return MOCK_SDK.equals(Build.MODEL) || MOCK_GSDK.equals(Build.MODEL);
        }
        return false;
    }

    /**
     * 获得设备IMEI号码
     *
     * @return IMEI号码
     * @author Wythe
     */
    public static String getImei() {
        final TelephonyManager telephonyManager = DevManager.Instance.getTelephony();
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getDeviceId();
    }

    /**
     * 获得设备IMSI号码
     *
     * @return IMSI号码
     * @author Wythe
     */
    public static String getImsi() {
        final TelephonyManager telephonyManager = DevManager.Instance.getTelephony();
        if (telephonyManager == null) {
            return null;
        }
        return telephonyManager.getSubscriberId();
    }

    private enum DevManager {
        Instance;

        final TelephonyManager getTelephony() {
            final Context context = SproutLib.getContext();
            if (context == null) {
                return null;
            }
            return (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        }
    }

}
