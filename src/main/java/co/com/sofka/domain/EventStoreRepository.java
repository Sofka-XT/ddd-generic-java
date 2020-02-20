package co.com.sofka.domain;

import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;


public interface EventStoreRepository {
    List<DomainEvent> getEventsBy(AggregateRootId aggregateRootId);
    void saveEventsWithAn(AggregateRootId aggregateRootId, List<DomainEvent> events);
}
