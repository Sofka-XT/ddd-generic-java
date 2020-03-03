package co.com.sofka.infraestructure.handle;

public final class CommandHandlerExecutionError extends Exception {
    public CommandHandlerExecutionError(Throwable cause) {
        super(cause);
    }
}
