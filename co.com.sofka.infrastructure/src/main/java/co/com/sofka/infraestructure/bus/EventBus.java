package co.com.sofka.infraestructure.bus;

import co.com.sofka.domain.generic.DomainEvent;

/**
 * The interface Event bus.
 */
public interface EventBus {
    /**
     * Publish.
     *
     * @param event the event
     */
    void publish(final DomainEvent event);

    /**
     * Publish error.
     *
     * @param errorEvent the error event
     */
    void publishError(final ErrorEvent errorEvent);
}
