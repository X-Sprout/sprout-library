/**
 * Created on 2016/2/26
 */
package org.sprout.core.logging.api;

import org.sprout.core.logging.Lc;
import org.sprout.core.logging.LogcatLevel;

/**
 * 日志工作者接口
 * <p/>
 *
 * @author Wythe
 */
public abstract class ALogcatWorker {

    protected final String tag;

    public ALogcatWorker(final String tag) {
        this.tag = tag;
    }

    public abstract void write(final LogcatLevel level, final String label, final String local, final String print, final Throwable error, final Lc.Formater style);

}
