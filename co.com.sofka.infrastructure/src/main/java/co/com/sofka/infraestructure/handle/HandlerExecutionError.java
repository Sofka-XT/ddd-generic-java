package co.com.sofka.infraestructure.handle;

/**
 * The type Command handler execution error.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public final class HandlerExecutionError extends Exception {
    /**
     * Instantiates a new Command handler execution error.
     *
     * @param cause the cause
     */
    public HandlerExecutionError(Throwable cause) {
        super(cause);
    }
}
