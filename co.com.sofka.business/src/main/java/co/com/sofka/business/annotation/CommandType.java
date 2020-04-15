package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface command type.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface CommandType {
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
