package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Query handles.
 * <p>
 * This annotation determines the path of query.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface QueryPath {
    /**
     * The path of the query.
     *
     * @return the path
     */
    String name();
}
