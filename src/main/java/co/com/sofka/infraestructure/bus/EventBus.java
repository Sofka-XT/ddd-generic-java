package co.com.sofka.infraestructure.bus;

import co.com.sofka.domain.generic.DomainEvent;

public interface EventBus {
    void publish(final DomainEvent event);
}
