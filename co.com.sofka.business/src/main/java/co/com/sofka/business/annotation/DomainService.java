package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Domain service.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface DomainService {
}
