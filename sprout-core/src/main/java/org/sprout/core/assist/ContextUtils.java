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
        Context base = context;
        do {
            if (!(base instanceof ContextWrapper)) {
                return null;
            }
            base = ((ContextWrapper) base).getBaseContext();
        } while (!(base instanceof Activity));
        // 类型转换
        return (Activity) base;
    }

}
