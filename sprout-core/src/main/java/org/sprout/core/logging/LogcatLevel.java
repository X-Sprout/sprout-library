/**
 * Created on 2016/2/25
 */
package org.sprout.core.logging;

import android.util.Log;

/**
 * 日志等级类
 * <p/>
 *
 * @author Wythe
 */
public enum LogcatLevel {

    NONE(Integer.MAX_VALUE), INFO(Log.INFO), WARN(Log.WARN), DEBUG(Log.DEBUG), ERROR(Log.ERROR), ASSERT(Log.ASSERT), VERBOSE(Log.VERBOSE);

    private final int value;

    LogcatLevel(final int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
