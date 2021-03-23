package co.com.sofka.business.generic;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * The type Service builder.
 */
public class ServiceBuilder {
    private final Map<String, Object> builder = new ConcurrentHashMap<>();

    /**
     * Add service service builder.
     *
     * @param object the object
     * @return the service builder
     */
    public ServiceBuilder addService(Object object) {
        builder.put(object.getClass().getCanonicalName(), object);
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
        return builder.values().stream().filter(clasz::isInstance)
                .map(inst -> (T) inst)
                .findFirst();
    }

    /**
     * Exist boolean.
     *
     * @param loadClass the load class
     * @return the boolean
     */
    public boolean exist(Class<?> loadClass) {
        return builder.containsKey(loadClass.getCanonicalName());
    }
}
