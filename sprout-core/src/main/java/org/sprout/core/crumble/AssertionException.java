/**
 * Created on 2016/2/26
 */
package org.sprout.core.crumble;

/**
 * 断言异常
 * <p/>
 *
 * @author Wythe
 */
public class AssertionException extends RuntimeException {
    /**
     * 断言异常构造器
     *
     * @param error 异常信息
     */
    public AssertionException(final String error) {
        super(error);
    }

    /**
     * 断言异常构造器
     *
     * @param cause 异常对象
     */
    public AssertionException(final Throwable cause) {
        super(cause);
    }

    /**
     * 断言异常构造器
     *
     * @param error 异常信息
     * @param cause 异常对象
     */
    public AssertionException(final String error, final Throwable cause) {
        super(error, cause);
    }
}