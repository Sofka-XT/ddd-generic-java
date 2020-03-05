package co.com.sofka.infraestructure.bus;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.infraestructure.handle.CommandHandlerExecutionError;

/**
 * The interface Command bus.
 */
@FunctionalInterface
public interface CommandBus {
    /**
     * Dispatch.
     *
     * @param command the command
     * @throws CommandHandlerExecutionError the command handler execution error
     */
    void dispatch(Command command) throws CommandHandlerExecutionError;
}
