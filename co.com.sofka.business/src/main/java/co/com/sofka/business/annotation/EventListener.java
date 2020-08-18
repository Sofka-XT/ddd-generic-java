package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Business login.
 * <p>
 * This annotation is to identify the business logic given a use case
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface EventListener {
    /**
     * Name of the type event.
     *
     * @return the type name of the event
     */
    String eventType();
}