package co.com.sofka.infraestructure.repository;

import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;


public interface EventStoreRepository<T extends AggregateRootId> {
    List<DomainEvent> getEventsBy(T aggregateRootId) throws QueryFaultException;
    void saveEvent(T aggregateRootId, DomainEvent event);
}
