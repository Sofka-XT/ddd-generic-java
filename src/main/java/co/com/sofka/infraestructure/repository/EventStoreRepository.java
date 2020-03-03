package co.com.sofka.infraestructure.repository;

import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.store.StoredEvent;

import java.util.List;


public interface EventStoreRepository<T extends AggregateRootId> {
    List<DomainEvent> getEventsBy(T aggregateRootId) throws QueryFaultException;
    void saveEvent(T aggregateRootId, StoredEvent storedEvent);
}
