/**
 * Created on 2016/2/26
 */
package org.sprout.core.logging;

import android.util.Log;

import org.sprout.SproutLib;
import org.sprout.core.assist.ReflectUtils;
import org.sprout.core.assist.StringUtils;
import org.sprout.core.logging.api.ALogcatWorker;

import java.lang.reflect.Field;

/**
 * 日志服务类
 * <p/>
 *
 * @author Wythe
 */
public final class LogcatService {

    static String TAG;

    static boolean READY;

    static ALogcatWorker worker;

    private final static String FIELD_V = "V";
    private final static String FIELD_D = "D";
    private final static String FIELD_I = "I";
    private final static String FIELD_W = "W";
    private final static String FIELD_E = "E";
    private final static String FIELD_A = "A";

    private final static String FIELD_B = "FALSE";

    private final static String LOGCAT_IMPL_LOAD = "org.sprout.core.logging.impl.StaticLogcatWorker";

    /**
     * 初始化日志服务
     *
     * @param label 日志标签
     * @author Wythe
     */
    public static void init(final String label) {
        LogcatService.init(label, LogcatLevel.ERROR);
    }

    /**
     * 初始化日志服务
     *
     * @param label 日志标签
     * @param level 记录等级
     * @author Wythe
     */
    public static void init(final String label, final LogcatLevel level) {
        if (!LogcatService.READY) {
            if (StringUtils.isEmpty(label)) {
                throw new RuntimeException("Initialize the logcat tag must be not null.");
            }
            // 初始化工作者
            final int setup = (level == null ? LogcatLevel.NONE.getValue() : level.getValue());
            try {
                LogcatService.worker = (ALogcatWorker) Class.forName(LOGCAT_IMPL_LOAD).getDeclaredConstructor(String.class).newInstance(label);
            } catch (ClassNotFoundException e) {
                Log.e(SproutLib.name, "Failed to find class " + StringUtils.DQUOTE + LOGCAT_IMPL_LOAD + StringUtils.DQUOTE + ".");
                return;
            } catch (ClassCastException e) {
                Log.e(SproutLib.name, "Failed to cast class " + StringUtils.DQUOTE + LOGCAT_IMPL_LOAD + StringUtils.DQUOTE + ".");
                return;
            } catch (Exception e) {
                Log.e(SproutLib.name, "Failed to load class " + StringUtils.DQUOTE + LOGCAT_IMPL_LOAD + StringUtils.DQUOTE + ".");
                return;
            }
            // 初始化日志器
            if (LogcatService.worker != null) {
                Field B_FIELD;
                try {
                    B_FIELD = Boolean.class.getField(FIELD_B);
                } catch (NoSuchFieldException e) {
                    return;
                }
                // 初始化日志等级
                if (B_FIELD != null) {
                    final boolean access = B_FIELD.isAccessible();
                    if (!access) {
                        B_FIELD.setAccessible(true);
                    }
                    try {
                        // 是否记录V
                        final Field V_FIELD = ReflectUtils.findField(Lc.class, FIELD_V, Boolean.class, false);
                        if (V_FIELD != null) {
                            ReflectUtils.setField(B_FIELD, V_FIELD, LogcatLevel.VERBOSE.getValue() >= setup);
                        }
                        // 是否记录D
                        final Field D_FIELD = ReflectUtils.findField(Lc.class, FIELD_D, Boolean.class, false);
                        if (D_FIELD != null) {
                            ReflectUtils.setField(B_FIELD, D_FIELD, LogcatLevel.DEBUG.getValue() >= setup);
                        }
                        // 是否记录I
                        final Field I_FIELD = ReflectUtils.findField(Lc.class, FIELD_I, Boolean.class, false);
                        if (I_FIELD != null) {
                            ReflectUtils.setField(B_FIELD, I_FIELD, LogcatLevel.INFO.getValue() >= setup);
                        }
                        // 是否记录W
                        final Field W_FIELD = ReflectUtils.findField(Lc.class, FIELD_W, Boolean.class, false);
                        if (W_FIELD != null) {
                            ReflectUtils.setField(B_FIELD, W_FIELD, LogcatLevel.WARN.getValue() >= setup);
                        }
                        // 是否记录E
                        final Field E_FIELD = ReflectUtils.findField(Lc.class, FIELD_E, Boolean.class, false);
                        if (E_FIELD != null) {
                            ReflectUtils.setField(B_FIELD, E_FIELD, LogcatLevel.ERROR.getValue() >= setup);
                        }
                        // 是否记录A
                        final Field A_FIELD = ReflectUtils.findField(Lc.class, FIELD_A, Boolean.class, false);
                        if (A_FIELD != null) {
                            ReflectUtils.setField(B_FIELD, A_FIELD, LogcatLevel.ASSERT.getValue() >= setup);
                        }
                    } catch (Exception e) {
                        return;
                    } finally {
                        if (!access) {
                            B_FIELD.setAccessible(access);
                        }
                    }
                    // 初始化成功
                    LogcatService.TAG = label;
                    LogcatService.READY = true;
                }
            }
        }
    }

}
