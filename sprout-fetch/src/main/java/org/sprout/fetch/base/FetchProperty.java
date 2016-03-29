/**
 * Created on 2016/2/23
 */
package org.sprout.fetch.base;

import java.io.Serializable;

/**
 * 网络服务属性抽象类
 * <p/>
 *
 * @author Cuzki
 */
public abstract class FetchProperty implements Serializable, Cloneable {

    protected final String taskId;

    protected FetchProperty(final String taskId) {
        this.taskId = taskId;
    }

    public String getTaskId() {
        return taskId;
    }

}
