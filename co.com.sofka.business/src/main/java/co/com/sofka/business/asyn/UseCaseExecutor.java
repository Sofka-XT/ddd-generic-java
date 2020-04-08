package co.com.sofka.business.asyn;

import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.Command;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.generic.Identity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

/**
 * The type Use case executor.
 *
 * @param <T> the type parameter
 */
public abstract class UseCaseExecutor<T extends Command> implements Consumer<T> {
    private Flow.Subscriber<? super DomainEvent> subscriberEvent;
    private List<DomainEvent> domainEvents;
    private Optional<DomainEventRepository> repository;
    private Optional<Identity> aggregateId;

    /**
     * With subscriber event use case executor.
     *
     * @param subscriberEvent the subscriber event
     * @return the use case executor
     */
    public UseCaseExecutor<T> withSubscriberEvent(Flow.Subscriber<? super DomainEvent> subscriberEvent) {
        this.subscriberEvent = subscriberEvent;
        return this;
    }

    /**
     * With domain events use case executor.
     *
     * @param domainEvents the domain events
     * @return the use case executor
     */
    public UseCaseExecutor<T> withDomainEvents(List<DomainEvent> domainEvents) {
        this.domainEvents = domainEvents;
        return this;
    }

    /**
     * With domain event repo use case executor.
     *
     * @param repository the repository
     * @return the use case executor
     */
    public UseCaseExecutor<T> withDomainEventRepo(DomainEventRepository repository) {
        this.repository = Optional.of(repository);
        return this;
    }

    /**
     * With aggregate id use case executor.
     *
     * @param aggregateId the aggregate id
     * @return the use case executor
     */
    public UseCaseExecutor<T> withAggregateId(Identity aggregateId) {
        this.aggregateId = Optional.of(aggregateId);
        return this;
    }

    /**
     * Subscriber event flow . subscriber.
     *
     * @return the flow . subscriber
     */
    public Flow.Subscriber<? super DomainEvent> subscriberEvent() {
        return subscriberEvent;
    }

    /**
     * Domain events list.
     *
     * @return the list
     */
    public List<DomainEvent> domainEvents() {
        return  Optional.ofNullable(domainEvents)
                .orElse(repository.orElseGet(() -> aggregateRootId -> new ArrayList<>())
                        .getEventsBy(aggregateId.orElse(null)));
    }
}
