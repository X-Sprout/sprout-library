/**
 * Created on 2016/2/24
 */
package org.sprout.fetch.base;

/**
 * 网络服务期望抽象类
 * <p/>
 *
 * @author Cuzki
 */
public abstract class FetchPromiser<H extends FetchPromiser, P extends FetchProperty, L extends FetchListener> {

    protected final String taskId;

    protected FetchPromiser(final String taskId) {
        this.taskId = taskId;
    }

    public abstract P info();

    public abstract H then(final L listener);

}
