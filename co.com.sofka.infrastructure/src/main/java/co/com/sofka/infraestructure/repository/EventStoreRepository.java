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
     * @param aggregateName   the aggregate name
     * @param aggregateRootId the aggregate root id
     * @return the events by
     */
    List<DomainEvent> getEventsBy(String aggregateName, String aggregateRootId);

    /**
     * Save event.
     *
     * @param aggregateName   the aggregate name
     * @param aggregateRootId the aggregate root id
     * @param storedEvent     the stored event
     */
    void saveEvent(String aggregateName, String aggregateRootId, StoredEvent storedEvent);

}
