/**
 * Created on 2016/2/25
 */
package org.sprout.fetch.spec;

/**
 * 网络服务状态类
 * <p/>
 *
 * @author Wythe
 */
public enum FetchStatus {

    FINISH(0), START(1), AWAIT(2), PAUSE(3), ERROR(-1);

    private final int value;

    FetchStatus(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
