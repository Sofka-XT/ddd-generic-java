package co.com.sofka.business.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServiceBuilder {
    private final List<Object> builder = new ArrayList<>();

    public ServiceBuilder addService(Object object){
        builder.add(object);
        return this;
    }

    public <T> Optional<T> getService(Class<T> clasz){
        return builder.stream().filter(clasz::isInstance)
                .map(inst -> (T)inst)
                .findFirst();
    }

}
