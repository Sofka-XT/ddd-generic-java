package co.com.sofka.infrastructure;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.ErrorEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InMemoryEvenBus implements EventBus {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryEvenBus.class);

    @Override
    public void publish(DomainEvent event) {
        logger.info("publish -> " + event.type);
    }

    @Override
    public void publishError(ErrorEvent errorEvent) {
        logger.info("error -> " + errorEvent.toString());
    }
}
