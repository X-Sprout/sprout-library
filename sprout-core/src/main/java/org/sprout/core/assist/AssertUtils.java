/**
 * Created on 2016/2/26
 */
package org.sprout.core.assist;

import org.sprout.core.crumble.AssertionException;

/**
 * 断言工具类
 * <p/>
 *
 * @author Wythe
 */
public final class AssertUtils {

    /**
     * 断言是否为空
     * <p/>
     *
     * @param object 对象
     * @author Wythe
     */
    public static void isNull(final Object object) {
        AssertUtils.isNull(object, "[Assertion failed] - The object argument must be null.");
    }

    /**
     * 断言是否为空
     * <p/>
     *
     * @param object  对象
     * @param message 异常信息
     * @author Wythe
     */
    public static void isNull(final Object object, final String message) {
        AssertUtils.isNull(object, new AssertionException(message));
    }

    /**
     * 断言是否为空
     * <p/>
     *
     * @param object    对象
     * @param exception 异常对象
     * @author Wythe
     */
    public static void isNull(final Object object, final RuntimeException exception) {
        if (object != null) {
            throw exception;
        }
    }

    /**
     * 断言是否为真
     * <p/>
     *
     * @param express 表达式
     * @author Wythe
     */
    public static void isTrue(final boolean express) {
        AssertUtils.isTrue(express, "[Assertion failed] - The expression must be true.");
    }

    /**
     * 断言是否为真
     * <p/>
     *
     * @param express 表达式
     * @param message 异常信息
     * @author Wythe
     */
    public static void isTrue(final boolean express, final String message) {
        AssertUtils.isTrue(express, new AssertionException(message));
    }

    /**
     * 断言是否为真
     * <p/>
     *
     * @param express   对象
     * @param exception 异常对象
     * @author Wythe
     */
    public static void isTrue(final boolean express, final RuntimeException exception) {
        if (!express) {
            throw exception;
        }
    }

    /**
     * 断言是否为假
     * <p/>
     *
     * @param express 表达式
     * @author Wythe
     */
    public static void isFalse(final boolean express) {
        AssertUtils.isTrue(express, "[Assertion failed] - The expression must be false.");
    }

    /**
     * 断言是否为假
     * <p/>
     *
     * @param express 表达式
     * @param message 异常信息
     * @author Wythe
     */
    public static void isFalse(final boolean express, final String message) {
        AssertUtils.isFalse(express, new AssertionException(message));
    }

    /**
     * 断言是否为假
     * <p/>
     *
     * @param express   表达式
     * @param exception 异常对象
     * @author Wythe
     */
    public static void isFalse(final boolean express, final RuntimeException exception) {
        if (express) {
            throw exception;
        }
    }

}