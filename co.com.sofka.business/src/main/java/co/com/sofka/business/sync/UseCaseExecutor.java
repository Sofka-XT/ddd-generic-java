package co.com.sofka.business.sync;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;
import java.util.function.Function;

/**
 * The type Use case executor.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 */
public abstract class UseCaseExecutor<T, R extends UseCase.ResponseValues> implements Function<T, R> {
    private List<DomainEvent> domainEvents;

    /**
     * With domain events use case executor.
     *
     * @param domainEvents the domain events
     * @return the use case executor
     */
    public UseCaseExecutor<T, R> withDomainEvents(List<DomainEvent> domainEvents) {
        this.domainEvents = domainEvents;
        return this;
    }

    /**
     * Domain events list.
     *
     * @return the list
     */
    public List<DomainEvent> domainEvents() {
        return domainEvents;
    }
}
