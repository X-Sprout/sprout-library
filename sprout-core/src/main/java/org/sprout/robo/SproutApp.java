/**
 * Created on 2016/3/17
 */
package org.sprout.robo;

import android.app.Application;
import android.support.annotation.CallSuper;

import org.sprout.SproutLib;

/**
 * Sprout应用基类
 * <p/>
 *
 * @author Wythe
 */
public abstract class SproutApp extends Application {

    @Override
    @CallSuper
    public final void onCreate() {
        super.onCreate();
        SproutLib.init(this);
    }

}
