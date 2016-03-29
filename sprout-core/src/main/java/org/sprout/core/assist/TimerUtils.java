/**
 * Created on 2016/3/7
 */
package org.sprout.core.assist;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import org.sprout.SproutLib;
import org.sprout.core.logging.Lc;

/**
 * 定时器工具
 * <p/>
 *
 * @author Wythe
 */
public final class TimerUtils {

    private static final int MSG_WHAT_ON_TIME = 0xA8;

    /**
     * 定时监听
     * <p/>
     *
     * @author Wythe
     */
    public interface Listener {
        void onTime();
    }

    private final Handler mOperator;

    private TimerUtils(final Looper looper, final long interval, final long duration, final TimerUtils.Listener listener) {
        if (listener == null) {
            this.mOperator = null;
        } else {
            (this.mOperator = new Handler(looper) {
                @Override
                public void handleMessage(final Message msg) {
                    stop();
                    try {
                        listener.onTime();
                    } catch (Exception e) {
                        if (Lc.E) {
                            Lc.t(SproutLib.name).e(e);
                        }
                    } finally {
                        if (interval > 0) {
                            this.sendMessageDelayed(this.obtainMessage(MSG_WHAT_ON_TIME), interval);
                        }
                    }
                }
            }).sendMessageDelayed(this.mOperator.obtainMessage(MSG_WHAT_ON_TIME), (duration > 0 ? duration : 0));
        }
    }

    /**
     * 延迟执行
     *
     * @param duration 延迟时长
     * @param listener 延迟监听
     * @return 定时工具
     * @author Wythe
     */
    public static TimerUtils delay(final long duration, final TimerUtils.Listener listener) {
        return TimerUtils.repet(0, duration, null, listener);
    }

    /**
     * 延迟执行
     *
     * @param duration 延迟时长
     * @param operator 操作线程
     * @param listener 延迟监听
     * @return 定时工具
     * @author Wythe
     */
    public static TimerUtils delay(final long duration, final HandlerThread operator, final TimerUtils.Listener listener) {
        return TimerUtils.repet(0, duration, operator, listener);
    }

    /**
     * 重复执行
     *
     * @param interval 重复间隔
     * @param listener 重复监听
     * @return 定时工具
     * @author Wythe
     */
    public static TimerUtils repet(final long interval, final TimerUtils.Listener listener) {
        return TimerUtils.repet(interval, 0, listener);
    }

    /**
     * 重复执行
     *
     * @param interval 重复间隔
     * @param duration 延迟时长（首次）
     * @param listener 重复监听
     * @return 定时工具
     * @author Wythe
     */
    public static TimerUtils repet(final long interval, final long duration, final TimerUtils.Listener listener) {
        return TimerUtils.repet(interval, duration, null, listener);
    }

    /**
     * 重复执行
     *
     * @param interval 重复间隔
     * @param operator 操作线程
     * @param listener 重复监听
     * @return 定时工具
     * @author Wythe
     */
    public static TimerUtils repet(final long interval, final HandlerThread operator, final TimerUtils.Listener listener) {
        return TimerUtils.repet(interval, 0, operator, listener);
    }

    /**
     * 重复执行
     *
     * @param interval 重复间隔
     * @param duration 延迟时长（首次）
     * @param operator 操作线程
     * @param listener 重复监听
     * @return 定时工具
     * @author Wythe
     */
    public static TimerUtils repet(final long interval, final long duration, final HandlerThread operator, final TimerUtils.Listener listener) {
        if ((interval > 0 || duration > 0) && listener != null) {
            final boolean custom = (operator != null);
            if (custom) {
                operator.start();
            }
            return new TimerUtils((custom ? operator.getLooper() : Looper.getMainLooper()), interval, duration, listener);
        }
        return null;
    }

    /**
     * 停止执行
     *
     * @author Wythe
     */
    public void stop() {
        if (this.mOperator != null) {
            this.mOperator.removeMessages(MSG_WHAT_ON_TIME);
        }
    }

}
