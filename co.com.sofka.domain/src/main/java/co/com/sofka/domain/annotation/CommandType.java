package co.com.sofka.domain.annotation;

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
}
