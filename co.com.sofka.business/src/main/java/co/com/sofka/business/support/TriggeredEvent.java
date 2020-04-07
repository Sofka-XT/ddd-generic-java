package co.com.sofka.business.support;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.generic.DomainEvent;

/**
 * The type Triggered event.
 *
 * @param <T> the type parameter
 */
public final class TriggeredEvent<T extends DomainEvent> implements UseCase.RequestEvent {
    private final T event;

    /**
     * Instantiates a new Triggered event.
     *
     * @param event the event
     */
    public TriggeredEvent(T event) {
        this.event = event;
    }

    @Override
    public T getDomainEvent() {
        return event;
    }
}