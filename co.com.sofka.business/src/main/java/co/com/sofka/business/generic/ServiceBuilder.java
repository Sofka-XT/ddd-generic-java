package co.com.sofka.business.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Builder
 */
public class ServiceBuilder {
    private final List<Object> builder = new ArrayList<>();

    /**
     * Add new service
     * @param object
     * @return ServiceBuilder
     */
    public ServiceBuilder addService(Object object) {
        builder.add(object);
        return this;
    }

    /**
     * Get service fiend
     *
     * @param class optional
     * @return service class fined
     */
    public <T> Optional<T> getService(Class<T> clasz) {
        return builder.stream().filter(clasz::isInstance)
                .map(inst -> (T) inst)
                .findFirst();
    }

}
