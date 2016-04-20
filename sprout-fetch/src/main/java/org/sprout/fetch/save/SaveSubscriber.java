/**
 * Created on 2016/2/23
 */
package org.sprout.fetch.save;

import org.sprout.SproutLib;
import org.sprout.core.assist.CollectionUtils;
import org.sprout.core.assist.StringUtils;
import org.sprout.core.logging.Lc;
import org.sprout.fetch.spec.FetchStatus;

import java.util.Set;

import rx.Subscriber;

/**
 * 下载订阅对象类
 * <p/>
 *
 * @author Cuzki
 */
final class SaveSubscriber extends Subscriber<SaveProperty> {

    private final String saveId;

    SaveSubscriber(final String saveId) {
        this.saveId = saveId;
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(final Throwable e) {
    }

    @Override
    public void onNext(final SaveProperty property) {
        if (!StringUtils.isEmpty(saveId) && property != null) {
            final String taskId = property.getTaskId();
            if (!StringUtils.isEmpty(taskId)) {
                if (!FetchStatus.PAUSE.equals(property.getSaveStatus()) && taskId.equals(saveId)) {
                    final Set<SaveListener> callbackList = SaveObserver.searchListener(saveId);
                    if (!CollectionUtils.isEmpty(callbackList)) {
                        for (final SaveListener saveListener : callbackList) {
                            if (saveListener != null) {
                                try {
                                    saveListener.onUpdate(property);
                                } catch (Exception e) {
                                    if (Lc.E) {
                                        Lc.t(SproutLib.name).e("FetchService save update callback exception.", e);
                                    }
                                }
                            }
                        }
                    }
                }
                if (Lc.D) {
                    Lc.t(SproutLib.name).d("FetchService save update: " + taskId + " -> " + property.getSaveSize());
                }
            }
        }
    }

}
