/**
 * Created on 2016/2/26
 */
package org.sprout.core.logging.impl;

import com.orhanobut.logger.Logger;

import org.sprout.core.assist.StringUtils;
import org.sprout.core.logging.Lc;
import org.sprout.core.logging.LogcatLevel;
import org.sprout.core.logging.api.ALogcatWorker;

/**
 * Logger日志适配器
 * <p/>
 *
 * @author Wythe
 */
public final class StaticLogcatWorker extends ALogcatWorker {

    public StaticLogcatWorker(final String tag) {
        super(tag);
        // 初始化日志
        Logger.init(tag);
    }

    @Override
    public void write(final LogcatLevel level, final String label, final String local, final String print, final Throwable error, final Lc.Formater style) {
        if (!StringUtils.isEmpty(print)) {
            if (style == null) {
                switch (level) {
                    case VERBOSE: {
                        Logger.t(label).v(print);
                        break;
                    }
                    case INFO: {
                        Logger.t(label).i(print);
                        break;
                    }
                    case WARN: {
                        Logger.t(label).w(print);
                        break;
                    }
                    case DEBUG: {
                        Logger.t(label).d(print);
                        break;
                    }
                    case ERROR: {
                        Logger.t(label).e(print);
                        break;
                    }
                    case ASSERT: {
                        Logger.t(label).wtf(print);
                        break;
                    }
                }
            } else {
                if (Lc.Formater.XML.equals(style)) {
                    Logger.t(label).xml(print);
                }
                if (Lc.Formater.JSN.equals(style)) {
                    Logger.t(label).json(print);
                }
            }
        }
        if (error != null) {
            Logger.t(label).e(error, null);
        }
    }

}
