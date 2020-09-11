package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Command type.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface CommandType {
    /**
     * Name string.
     *
     * @return the string
     */
    String name();

    /**
     * Aggregate string.
     *
     * @return the string
     */
    String aggregate();
}
