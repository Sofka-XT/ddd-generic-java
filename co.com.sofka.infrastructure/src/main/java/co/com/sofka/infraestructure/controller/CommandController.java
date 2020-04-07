package co.com.sofka.infraestructure.controller;


import co.com.sofka.domain.generic.Command;
import co.com.sofka.infraestructure.bus.CommandBus;
import co.com.sofka.infraestructure.handle.HandlerExecutionError;

/**
 * The type Command controller.
 */
public abstract class CommandController {
    private final CommandBus commandBus;

    /**
     * Instantiates a new Command controller.
     *
     * @param commandBus the command bus
     */
    public CommandController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    /**
     * Dispatch.
     *
     * @param command the command
     * @throws HandlerExecutionError the handler execution error
     */
    protected void dispatch(Command command) throws HandlerExecutionError {
        commandBus.dispatch(command);
    }
}