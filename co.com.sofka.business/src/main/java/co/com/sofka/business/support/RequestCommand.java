package co.com.sofka.business.support;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.generic.Command;

/**
 * The type Request command.
 *
 * @param <T> the type parameter
 */
public final class RequestCommand<T extends Command> implements UseCase.RequestValues {

    private final T command;

    /**
     * Instantiates a new Request command.
     *
     * @param command the command
     */
    public RequestCommand(T command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public T getCommand() {
        return command;
    }
}