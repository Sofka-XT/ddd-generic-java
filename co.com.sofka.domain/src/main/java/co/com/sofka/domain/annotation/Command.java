package co.com.sofka.domain.annotation;

import java.lang.annotation.*;

/**
 * The interface Command.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface Command {
    /**
     * Type string.
     *
     * @return the string
     */
    String type();
}
