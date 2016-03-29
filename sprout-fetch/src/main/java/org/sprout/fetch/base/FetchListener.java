/**
 * Created on 2016/2/23
 */
package org.sprout.fetch.base;

import android.app.Activity;
import android.content.Context;

/**
 * 监听抽象类
 * <p/>
 *
 * @author Wythe
 */
public abstract class FetchListener {

    protected final Context context;

    public FetchListener(final Context context) {
        this.context = context;
    }

    public boolean isValid() {
        return context != null && (!Activity.class.isInstance(context) || !((Activity) context).isFinishing());
    }

}
