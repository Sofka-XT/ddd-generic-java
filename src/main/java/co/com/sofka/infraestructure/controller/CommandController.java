package co.com.sofka.infraestructure.controller;


import co.com.sofka.domain.generic.Command;
import co.com.sofka.infraestructure.bus.CommandBus;
import co.com.sofka.infraestructure.handle.CommandHandlerExecutionError;

/**
 * The type Command controller.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
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
     * @throws CommandHandlerExecutionError the command handler execution error
     */
    protected void dispatch(Command command) throws CommandHandlerExecutionError {
        commandBus.dispatch(command);
    }
}