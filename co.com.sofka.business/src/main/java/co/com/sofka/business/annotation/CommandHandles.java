package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface Command handles.
 * <p>
 * This annotation is to identify the command handler, identify it to be injected into a command executor.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface CommandHandles {
}
