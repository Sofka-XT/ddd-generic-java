package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Query handles.
 * <p>
 * This annotation is to identify the query handler, identify it to be injected into a query executor.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface QueryHandles {
}
