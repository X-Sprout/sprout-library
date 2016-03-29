/**
 * Created on 2016/3/16
 */
package org.sprout.fetch;

/**
 * 网络服务异常类
 * <p/>
 *
 * @author Wythe
 */
public class FetchException extends Exception {

    private final String task;

    private final int code;

    /**
     * 网络服务异常构造器
     * <p/>
     *
     * @param task 任务标识
     * @param code 异常编码
     * @author Wythe
     */
    public FetchException(final String task, final int code) {
        this.task = task;
        this.code = code;
    }

    /**
     * 网络服务异常构造器
     * <p/>
     *
     * @param task  任务标识
     * @param code  异常编码
     * @param error 异常信息
     * @author Wythe
     */
    public FetchException(final String task, final int code, final String error) {
        super(error);
        this.task = task;
        this.code = code;
    }

    /**
     * 网络服务异常构造器
     * <p/>
     *
     * @param task  任务标识
     * @param code  异常编码
     * @param cause 异常对象
     * @author Wythe
     */
    public FetchException(final String task, final int code, final Throwable cause) {
        super(cause);
        this.task = task;
        this.code = code;
    }

    /**
     * 网络服务异常构造器
     * <p/>
     *
     * @param task  任务标识
     * @param code  异常编码
     * @param error 异常信息
     * @param cause 异常对象
     * @author Wythe
     */
    public FetchException(final String task, final int code, final String error, final Throwable cause) {
        super(error, cause);
        this.task = task;
        this.code = code;
    }

    /**
     * 获得网络服务异常代码
     * <p/>
     *
     * @return 异常代码
     * @author Wythe
     */
    public int getCode() {
        return code;
    }

    /**
     * 获得网络服务任务标识
     * <p/>
     *
     * @return 任务标识
     * @author Wythe
     */
    public String getTask() {
        return task;
    }

}
