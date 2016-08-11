/**
 * Created on 2016/8/11
 */
package org.sprout.core.assist;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.sprout.SproutLib;

/**
 * 键盘工具类
 * <p/>
 *
 * @author Wythe
 */
public final class KeyboardUtils {

    /**
     * 开关键盘
     *
     * @param editor 输入对象
     * @author Wythe
     */
    public static void toggle(final EditText editor) {
        if (editor != null) {
            editor.setFocusable(true);
            editor.setFocusableInTouchMode(true);
            editor.requestFocus();
            // 开关键盘
            final Context context = SproutLib.getContext();
            if (context != null) {
                final InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                }
            }
        }
    }

    /**
     * 显示键盘
     *
     * @param editor 输入对象
     * @author Wythe
     */
    public static void show(final EditText editor) {
        if (editor != null) {
            editor.setFocusable(true);
            editor.setFocusableInTouchMode(true);
            editor.requestFocus();
            // 显示键盘
            final Context context = SproutLib.getContext();
            if (context != null) {
                final InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.showSoftInput(editor, 0);
                }
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param editor 输入对象
     * @author Wythe
     */
    public static void hide(final EditText editor) {
        if (editor != null) {
            editor.clearFocus();
            // 隐藏键盘
            final Context context = SproutLib.getContext();
            if (context != null) {
                final InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputManager != null) {
                    inputManager.hideSoftInputFromWindow(editor.getWindowToken(), 0);
                }
            }
        }
    }

    /**
     * 隐藏键盘
     *
     * @param activity 活动页面
     * @author Wythe
     */
    public static void hide(final Activity activity) {
        if (activity != null) {
            final Window window = activity.getWindow();
            if (window != null) {
                final View view = window.peekDecorView();
                if (view != null) {
                    final InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (inputManager != null) {
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                    }
                }
            }
        }
    }

}
