package co.com.sofka.domain.generic;

/**
 * The interface Value object.
 *
 * @param <T> the type parameter
 */
public interface ValueObject<T> {
    /**
     * Value t.
     *
     * @return the t
     */
    T value();
}
