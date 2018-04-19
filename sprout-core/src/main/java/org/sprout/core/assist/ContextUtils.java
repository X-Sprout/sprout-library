/**
 * Created on 2018/2/08
 */
package org.sprout.core.assist;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;

/**
 * 上下文工具类
 * <p/>
 *
 * @author Wythe
 */
public final class ContextUtils {

    /**
     * 获得活动页
     *
     * @param context 上下文
     * @return 活动页
     * @author Wythe
     */
    public static Activity getActivity(final Context context) {
        if (context != null) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            if (context instanceof ContextWrapper) {
                return ContextUtils.getActivity(
                        ((ContextWrapper) context).getBaseContext()
                );
            }
        }
        return null;
    }

}
