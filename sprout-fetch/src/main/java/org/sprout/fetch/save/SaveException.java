/**
 * Created on 2016/2/29
 */
package org.sprout.fetch.save;

import org.sprout.fetch.FetchException;

/**
 * 下载服务异常类
 * <p/>
 *
 * @author Cuzki
 */
public final class SaveException extends FetchException {

    /**
     * 下载服务异常构造器
     * <p/>
     *
     * @param task 任务标识
     * @param code 异常编码
     * @author Wythe
     */
    public SaveException(final String task, final int code) {
        super(task, code);
    }

    /**
     * 下载服务异常构造器
     * <p/>
     *
     * @param task  任务标识
     * @param code  异常编码
     * @param error 异常信息
     * @author Wythe
     */
    public SaveException(final String task, final int code, final String error) {
        super(task, code, error);
    }

    /**
     * 下载服务异常构造器
     * <p/>
     *
     * @param task  任务标识
     * @param code  异常编码
     * @param cause 异常对象
     * @author Wythe
     */
    public SaveException(final String task, final int code, final Throwable cause) {
        super(task, code, cause);
    }

    /**
     * 下载服务异常构造器
     * <p/>
     *
     * @param task  任务标识
     * @param code  异常编码
     * @param error 异常信息
     * @param cause 异常对象
     * @author Wythe
     */
    public SaveException(final String task, final int code, final String error, final Throwable cause) {
        super(task, code, error, cause);
    }

}
