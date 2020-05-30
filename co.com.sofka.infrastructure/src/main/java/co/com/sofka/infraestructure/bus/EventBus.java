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
     * @param event the event
     */
    void publish(DomainEvent event);

    /**
     * Publish error.
     *
     * @param errorEvent the error event
     */
    void publishError(ErrorEvent errorEvent);
}
