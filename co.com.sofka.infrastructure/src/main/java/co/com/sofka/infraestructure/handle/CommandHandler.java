package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Command;

/**
 * The interface Command handler.
 *
 * @param <T> the type parameter
 */
@FunctionalInterface
public interface CommandHandler<T extends Command> {
    /**
     * Execute.
     *
     * @param command the command
     */
    void execute(T command);
}