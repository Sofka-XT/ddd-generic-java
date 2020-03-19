package co.com.sofka.infraestructure.annotation;

import java.lang.annotation.*;

/**
 * The interface Command handles.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface CommandHandles {
}
