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

}
