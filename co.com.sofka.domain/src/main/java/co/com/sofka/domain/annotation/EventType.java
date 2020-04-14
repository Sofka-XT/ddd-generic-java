package co.com.sofka.domain.annotation;

import java.lang.annotation.*;

/**
 * The interface event type.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface EventType {
    /**
     * Type string.
     *
     * @return the string
     */
    String name();
}
