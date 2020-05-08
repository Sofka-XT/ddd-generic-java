package co.com.sofka.infraestructure.bus;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.event.ErrorEvent;

/**
 * The interface Event bus.
 */
public interface EventBus {
    /**
     * Publish.
     *
     * @param exchange the exchange name
     * @param event    the event
     */
    void publish(String exchange, DomainEvent event);

    /**
     * Publish error.
     *
     * @param errorEvent the error event
     */
    void publishError(ErrorEvent errorEvent);
}
