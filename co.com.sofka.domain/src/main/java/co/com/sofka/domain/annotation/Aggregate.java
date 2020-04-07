package co.com.sofka.domain.annotation;

import java.lang.annotation.*;

/**
 * The interface Aggregate.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Aggregate {
}
