package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Query handles.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface QueryPath {
    /**
     * Type string.
     *
     * @return the string
     */
    String name();
}
