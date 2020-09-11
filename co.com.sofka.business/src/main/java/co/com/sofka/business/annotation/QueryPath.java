package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Query path.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface QueryPath {
    /**
     * Name string.
     *
     * @return the string
     */
    String name();
}
