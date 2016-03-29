/**
 * Created on 2016/2/23
 */
package org.sprout.fetch.save;

import org.sprout.core.assist.CollectionUtils;
import org.sprout.core.assist.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 下载观察对象类
 * <p/>
 *
 * @author Cuzki
 */
public final class SaveObserver {

    final static Map<String, List<SaveListener>> mListenerHash = new ConcurrentHashMap<>();

    static List<SaveListener> searchListener(final String saveId) {
        final List<SaveListener> resultList = SaveObserver.mListenerHash.get(saveId);
        if (!CollectionUtils.isEmpty(resultList)) {
            final List<SaveListener> invalidList = new ArrayList<>();
            for (final SaveListener callback : resultList) {
                if (callback == null || !callback.isValid()) {
                    invalidList.add(callback);
                }
            }
            if (invalidList.size() > 0) {
                resultList.removeAll(invalidList);
            }
        }
        return resultList;
    }

    /**
     * 注册下载观察者
     *
     * @param saveId   下载标识
     * @param listener 下载监听
     * @author Cuzki
     */
    public static void registListener(final String saveId, final SaveListener listener) {
        if (!StringUtils.isEmpty(saveId) && listener != null) {
            List<SaveListener> sendList = SaveObserver.mListenerHash.get(saveId);
            if (sendList != null) {
                if (!sendList.contains(listener)) {
                    sendList.add(listener);
                }
            } else {
                if ((sendList = new CopyOnWriteArrayList<>()).add(listener)) {
                    SaveObserver.mListenerHash.put(saveId, sendList);
                }
            }
        }
    }

    /**
     * 删除下载观察者
     *
     * @param saveId   下载标识
     * @param listener 下载监听
     * @author Cuzki
     */
    public static void removeListener(final String saveId, final SaveListener listener) {
        if (!StringUtils.isEmpty(saveId) && listener != null) {
            final List<SaveListener> sendList = SaveObserver.mListenerHash.get(saveId);
            if (!CollectionUtils.isEmpty(sendList)) {
                sendList.remove(listener);
            }
        }
    }

}
