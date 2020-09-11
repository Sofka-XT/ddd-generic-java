package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Extension service.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ExtensionService {
    /**
     * Value class [ ].
     *
     * @return the class [ ]
     */
    Class<?>[] value();
}
