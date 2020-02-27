package co.com.sofka.infraestructure.repository;

import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;


public interface EventStoreRepository {
    List<DomainEvent> getEventsBy(AggregateRootId aggregateRootId) throws QueryFaultException;
    void saveEvent(AggregateRootId aggregateRootId, DomainEvent event);
}
