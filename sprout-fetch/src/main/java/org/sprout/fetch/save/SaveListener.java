/**
 * Created on 2016/2/23
 */
package org.sprout.fetch.save;

import android.content.Context;

import org.sprout.fetch.base.FetchListener;

/**
 * 下载监听抽象类
 * <p/>
 *
 * @author Cuzki
 */
public abstract class SaveListener extends FetchListener {

    public SaveListener(final Context context) {
        super(context);
    }

    public abstract void onReady(final String saveId);

    public abstract void onError(final SaveException report);

    public abstract void onAwait(final SaveProperty property);

    public abstract void onStart(final SaveProperty property);

    public abstract void onPause(final SaveProperty property);

    public abstract void onFinish(final SaveProperty property);

    public abstract void onUpdate(final SaveProperty property);

}
