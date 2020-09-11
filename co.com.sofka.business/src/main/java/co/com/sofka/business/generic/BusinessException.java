package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.Command;

/**
 * The type Business exception.
 */
public class BusinessException extends UnexpectedException {

    private final Command command;

    /**
     * Instantiates a new Business exception.
     *
     * @param identify  the identify
     * @param message   the message
     * @param throwable the throwable
     * @param command   the command
     */
    public BusinessException(String identify, String message, Throwable throwable, Command command) {
        super(identify, message, throwable);
        this.command = command;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param identify  the identify
     * @param message   the message
     * @param throwable the throwable
     */
    public BusinessException(String identify, String message, Throwable throwable) {
        this(identify, message, throwable, null);
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param identify the identify
     * @param message  the message
     */
    public BusinessException(String identify, String message) {
        this(identify, message, null, null);
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
