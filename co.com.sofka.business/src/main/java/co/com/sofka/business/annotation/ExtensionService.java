package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Domain service.
 * <p>
 * This aggregate is to be injected as a domain service.
 * <p>
 * Doing immersion control
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ExtensionService {
    Class<?>[] value();
}
