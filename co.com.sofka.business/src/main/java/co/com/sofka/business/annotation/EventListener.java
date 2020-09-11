package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Event listener.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface EventListener {
    /**
     * Event type string.
     *
     * @return the string
     */
    String eventType();
}