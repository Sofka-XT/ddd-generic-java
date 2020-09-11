package co.com.sofka.business.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The type Service builder.
 */
public class ServiceBuilder {
    private final List<Object> builder = new ArrayList<>();

    /**
     * Add service service builder.
     *
     * @param object the object
     * @return the service builder
     */
    public ServiceBuilder addService(Object object) {
        builder.add(object);
        return this;
    }

    /**
     * Gets service.
     *
     * @param <T>   the type parameter
     * @param clasz the clasz
     * @return the service
     */
    public <T> Optional<T> getService(Class<T> clasz) {
        return builder.stream().filter(clasz::isInstance)
                .map(inst -> (T) inst)
                .findFirst();
    }

}
