/**
 * Created on 2016/2/26
 */
package org.sprout.core.assist;

/**
 * 触控工具类
 * <p/>
 *
 * @author Wythe
 */
public final class TouchUtils {

    private static long lastClickTime;

    /**
     * 判断是否快速点击
     *
     * @return 是否快速点击
     * @author Wythe
     */
    public static boolean isFastClick() {
        return TouchUtils.isFastClick(800);
    }

    /**
     * 判断是否快速点击
     *
     * @param interval 点击时间间隔
     * @return 是否快速点击
     * @author Wythe
     */
    public static boolean isFastClick(final int interval) {
        final long time = System.currentTimeMillis() - lastClickTime;
        if (time > 0 && time < interval) {
            return true;
        } else {
            lastClickTime = time;
        }
        return false;
    }

}
