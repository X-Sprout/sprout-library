/**
 * Created on 2016/2/24
 */
package org.sprout.fetch.spec;

/**
 * 网络服务优先级枚举类
 * <p/>
 *
 * @author Cuzki
 */
public enum FetchPrior {

    NOW(2), HIGH(1), NORM(0);

    private final int value;

    FetchPrior(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
