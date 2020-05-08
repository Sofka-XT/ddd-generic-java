package co.com.sofka.domain.generic;

import java.io.Serializable;

/**
 * The interface Value object.
 *
 * @param <T> the type parameter
 */
public interface ValueObject<T> extends Serializable {
    /**
     * Value object.
     *
     * @return the object resulted
     */
    T value();
}
