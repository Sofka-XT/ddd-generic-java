package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Command;

@FunctionalInterface
public interface CommandHandler<T extends Command> {
    void execute(T command);
}