package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.Command;

/**
 * Business exception
 * <p>
 * Business exceptions thrown by the use case
 */
public class BusinessException extends UnexpectedException {

    private final Command command;

    /**
     * Instantiates a new Business exception.
     *
     * @param identify the identify
     * @param message   the message
     * @param throwable the throwable and cause of the problem
     * @param command   the command executed
     */
    public BusinessException(String identify, String message, Throwable throwable, Command command) {
        super(identify, message, throwable);
        this.command = command;
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param identify the identify
     * @param message   the message
     * @param throwable the throwable and cause of the problem
     */
    public BusinessException(String identify,String message, Throwable throwable) {
        this(identify,message, throwable, null);
    }

    /**
     * Instantiates a new Business exception.
     *
     * @param identify the identify
     * @param message the message
     */
    public BusinessException(String identify,String message) {
        this(identify, message, null, null);
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
