/**
 * Created on 2016/3/21
 */
package org.sprout.core.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 事件总线接收注解
 * <p/>
 *
 * @author Wythe
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BindEvent {
    String value();
}
