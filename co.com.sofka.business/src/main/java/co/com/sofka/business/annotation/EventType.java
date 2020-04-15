package co.com.sofka.business.annotation;

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

    /**
     * Type string.
     *
     * @return the string
     */
    String aggregate();
}
