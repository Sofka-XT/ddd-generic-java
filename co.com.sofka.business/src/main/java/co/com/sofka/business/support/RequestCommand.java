package co.com.sofka.business.support;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.generic.Command;

/**
 * The type Request command.
 */
public final class RequestCommand implements UseCase.RequestValues {

    private final Command command;

    /**
     * Instantiates a new Request command.
     *
     * @param command the command
     */
    public RequestCommand(Command command) {
        this.command = command;
    }

    /**
     * Gets command.
     *
     * @return the command
     */
    public Command getCommand() {
        return command;
    }
}