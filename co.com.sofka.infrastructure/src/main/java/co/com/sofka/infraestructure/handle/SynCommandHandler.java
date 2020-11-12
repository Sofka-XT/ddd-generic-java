package co.com.sofka.infraestructure.handle;

import java.util.Objects;

/**
 * The interface Command handler.
 *
 * @param <T> the type parameter
 */
@FunctionalInterface
public interface SynCommandHandler<T, R> {
    /**
     * Execute.
     *
     * @param args the args
     */
    R execute(String commandType, T args);
}