package co.com.sofka.business.repository;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;

/**
 * The interface Domain event repository.
 */
public interface DomainEventRepository {
    /**
     * Gets events by.
     *
     * @param aggregateRootId the aggregate root id
     * @return the events by
     */
    List<DomainEvent> getEventsBy(String aggregateRootId);

    /**
     * Gets events by.
     *
     * @param aggregate       the aggregate
     * @param aggregateRootId the aggregate root id
     * @return the events by
     */
    List<DomainEvent> getEventsBy(String aggregate, String aggregateRootId);
}
