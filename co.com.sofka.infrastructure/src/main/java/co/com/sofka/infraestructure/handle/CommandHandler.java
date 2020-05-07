package co.com.sofka.infraestructure.handle;

/**
 * The interface Command handler.
 *
 * @param <T> the type parameter
 */
@FunctionalInterface
public interface CommandHandler<T> {
    /**
     * Execute.
     *
     * @param args the command
     */
    void execute(T args);
}