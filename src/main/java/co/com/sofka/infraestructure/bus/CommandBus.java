package co.com.sofka.infraestructure.bus;

import co.com.sofka.domain.generic.Command;
import co.com.sofka.infraestructure.handle.CommandHandlerExecutionError;

public interface CommandBus {
    void dispatch(Command command) throws CommandHandlerExecutionError;
}
