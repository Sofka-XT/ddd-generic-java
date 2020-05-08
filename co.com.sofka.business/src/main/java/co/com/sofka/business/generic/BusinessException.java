package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.Command;

/**
 * Business exception
 * <p>
 * Business exceptions thrown by the use case
 */
public class BusinessException extends RuntimeException {

    private final Command command;

    /**
     * Instantiates a new Business exception.
     *
     * @param message   the message
     * @param throwable the throwable and cause of the problem
     * @param command   the command executed
     */
    public BusinessException(String message, Throwable throwable, Command command) {
        super(message, throwable);
        this.command = command;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param message   the message
     * @param throwable the throwable and cause of the problem
     */
    public BusinessException(String message, Throwable throwable) {
        this(message, throwable, null);
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param message the message
     */
    public BusinessException(String message) {
        this(message, null, null);
    }

    /**
     * Command executed
     *
     * @return Command
     */
    public Command getCommand() {
        return command;
    }


}
