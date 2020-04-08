package co.com.sofka.business.repository;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Identity;

import java.util.List;

/**
 * The interface Domain event repository.
 *
 * @param <T> the type parameter
 */
public interface DomainEventRepository<T extends Identity> {
    /**
     * Gets events by.
     *
     * @param aggregateRootId the aggregate root id
     * @return the events by
     */
    List<DomainEvent> getEventsBy(T aggregateRootId);
}
