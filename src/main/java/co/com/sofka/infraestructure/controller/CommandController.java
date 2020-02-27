package co.com.sofka.infraestructure.controller;


import co.com.sofka.domain.generic.Command;
import co.com.sofka.infraestructure.bus.CommandBus;
import co.com.sofka.infraestructure.handle.CommandHandlerExecutionError;

public abstract class CommandController {
    private final CommandBus commandBus;

    public CommandController(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    protected void dispatch(Command command) throws CommandHandlerExecutionError {
        commandBus.dispatch(command);
    }
}