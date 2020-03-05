package co.com.sofka.bus;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.ErrorEvent;
import co.com.sofka.infraestructure.bus.EventBus;

public class InMemoryEvenBus implements EventBus {
    @Override
    public void publish(DomainEvent event) {

    }

    @Override
    public void publishError(ErrorEvent errorEvent) {

    }
}
