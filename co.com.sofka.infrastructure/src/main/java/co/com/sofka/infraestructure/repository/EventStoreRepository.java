package co.com.sofka.infraestructure.repository;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.store.StoredEvent;

import java.util.List;


/**
 * The interface Event store repository.
 */
public interface EventStoreRepository {
    /**
     * Gets events by.
     *
     * @param aggregateRootId the aggregate root id
     * @return the events by
     */
    List<DomainEvent> getEventsBy(String aggregateRootId);

    /**
     * Save event.
     *
     * @param aggregateRootId the aggregate root id
     * @param storedEvent     the stored event
     */
    void saveEvent(String aggregateRootId, StoredEvent storedEvent);

    /**
     * Select aggregate
     *
     * @param aggregate
     */
    void selectAggregate(String aggregate);
}
