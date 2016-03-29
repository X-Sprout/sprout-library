/**
 * Created on 2016/3/7
 */
package org.sprout.fetch.save;

import org.sprout.fetch.spec.FetchPrior;

import rx.Subscription;

/**
 * 下载观察对象类
 * <p/>
 *
 * @author Wythe
 */
final class SaveSubscription {

    private Subscription subscription;

    private SaveScheduler saveScheduler;

    SaveSubscription(final Subscription subscription, final SaveScheduler saveScheduler) {
        this.subscription = subscription;
        this.saveScheduler = saveScheduler;
    }

    boolean isUnsubscribed() {
        return this.subscription == null || this.subscription.isUnsubscribed();
    }

    void unsubscribe() {
        if (!this.isUnsubscribed()) {
            this.subscription.unsubscribe();
        }
        this.subscription = null;
        this.saveScheduler = null;
    }

    String getSaveId() {
        return this.saveScheduler != null ? this.saveScheduler.saveId : null;
    }

    FetchPrior getSavePrior() {
        if (this.saveScheduler != null) {
            for (final FetchPrior prior : FetchPrior.values()) {
                if (this.saveScheduler.savePrior == prior.getValue()) {
                    return prior;
                }
            }
        }
        return null;
    }

    SaveScheduler getSaveScheduler() {
        return this.saveScheduler;
    }

}
