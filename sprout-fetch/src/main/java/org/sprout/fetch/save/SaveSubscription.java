/**
 * Created on 2016/3/7
 */
package org.sprout.fetch.save;

import rx.Subscription;

/**
 * 下载观察对象类
 * <p/>
 *
 * @author Wythe
 */
final class SaveSubscription {

    private final Subscription subscription;

    private final SaveProperty saveProperty;

    SaveSubscription(final Subscription subscription, final SaveProperty saveProperty) {
        this.subscription = subscription;
        this.saveProperty = saveProperty;
    }

    SaveProperty getSaveProperty() {
        return this.saveProperty;
    }

    void unsubscribe() {
        if (this.isSubscribed()) {
            try {
                this.subscription.unsubscribe();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    boolean isSubscribed() {
        if (this.subscription != null) {
            try {
                return !this.subscription.isUnsubscribed();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

}
