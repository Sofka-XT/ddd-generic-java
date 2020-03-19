package co.com.sofka.business.support;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.generic.DomainEvent;

/**
 * The type Triggered event.
 *
 * @param <T> the type parameter
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
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

    /**
     * Gets domain event.
     *
     * @return the domain event
     */
    @Override
    public T getDomainEvent() {
        return event;
    }
}