package co.com.sofka.infraestructure.handle;

/**
 * The type Handler execution error.
 */
public final class HandlerExecutionError extends Exception {
    /**
     * Instantiates a new Handler execution error.
     *
     * @param cause the cause
     */
    public HandlerExecutionError(Throwable cause) {
        super(cause);
    }
}
