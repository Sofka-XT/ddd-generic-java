package co.com.sofka.infraestructure.repository;

import co.com.sofka.domain.generic.Identity;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.store.StoredEvent;

import java.util.List;


/**
 * The interface Event store repository.
 *
 * @param <T> the type parameter
 */
public interface EventStoreRepository<T extends Identity> {
    /**
     * Gets events by.
     *
     * @param aggregateRootId the aggregate root id
     * @return the events by
     * @throws QueryFaultException the query fault exception
     */
    List<DomainEvent> getEventsBy(T aggregateRootId) throws QueryFaultException;

    /**
     * Save event.
     *
     * @param aggregateRootId the aggregate root id
     * @param storedEvent     the stored event
     */
    void saveEvent(T aggregateRootId, StoredEvent storedEvent);
}
