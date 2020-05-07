package co.com.sofka.business.annotation;

import java.lang.annotation.*;

/**
 * The interface command type.
 * <p>
 * This annotation determines the type of command and the name of the aggregate in which it should be used to store the event thrown by the aggregate.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface CommandType {
    /**
     * Name of the type command.
     *
     * @return the type name of the command
     */
    String name();

    /**
     * String aggregate Name.
     *
     * @return the name of the aggregate
     */
    String aggregate();
}
