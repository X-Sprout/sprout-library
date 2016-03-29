/**
 * Created on 2016/3/17
 */
package org.sprout.core;

import java.io.Serializable;

import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * 事件总线类
 * <p/>
 *
 * @author Wythe
 */
public final class DroidBus {

    private final static Subject<Object, Object> emitter = new SerializedSubject<>(PublishSubject.create());

    /**
     * 事件消息器
     * <p/>
     *
     * @author Wythe
     */
    private final static class Messager implements Serializable {

        private final String event;

        private final Object[] annex;

        public Messager(final String event) {
            this(event, null);
        }

        public Messager(final String event, final Object[] annex) {
            this.event = event;
            this.annex = annex;
        }

    }

    public static void bind(final Object clazz) {
        if (clazz != null) {

        }
    }

    public static void bind(final DroidBus.Receiver radio) {
        if (radio != null) {

        }
    }

    public static void post(final String event) {

    }

    public static void post(final String event, final Object... annex) {

    }

    /**
     * 事件接收器
     * <p/>
     *
     * @author Wythe
     */
    public static abstract class Receiver {

        private final String[] route;

        public Receiver(final String... route) {
            this.route = route;
        }

        private void onEvent(final DroidBus.Messager msger) {
            if (msger != null) {

            }
        }

    }

}
