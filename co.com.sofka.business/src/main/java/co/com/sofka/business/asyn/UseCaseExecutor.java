package co.com.sofka.business.asyn;

import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

/**
 * The type Use case executor.
 *
 */
public abstract class UseCaseExecutor implements Consumer<Map<String, String>> {
    private Flow.Subscriber<? super DomainEvent> subscriberEvent;
    private List<DomainEvent> domainEvents;
    private DomainEventRepository repository;
    private String aggregateId;
    private String aggregateName;

    /**
     * With subscriber event use case executor.
     *
     * @param subscriberEvent the subscriber event
     * @return the use case executor
     */
    public UseCaseExecutor withSubscriberEvent(Flow.Subscriber<? super DomainEvent> subscriberEvent) {
        this.subscriberEvent = subscriberEvent;
        return this;
    }

    /**
     * With domain events use case executor.
     *
     * @param domainEvents the domain events
     * @return the use case executor
     */
    public UseCaseExecutor withDomainEvents(List<DomainEvent> domainEvents) {
        this.domainEvents = domainEvents;
        return this;
    }

    /**
     * With domain event repo use case executor.
     *
     * @param repository the repository
     * @return the use case executor
     */
    public UseCaseExecutor withDomainEventRepo(DomainEventRepository repository) {
        this.repository = repository;
        return this;
    }

    /**
     * With aggregate id use case executor.
     *
     * @param aggregateId the aggregate id
     * @return the use case executor
     */
    public UseCaseExecutor withAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
        return this;
    }

    /**
     * With aggregate id use case executor.
     *
     * @param aggregateName the aggregate name
     * @return the use case executor
     */
    public UseCaseExecutor withAggregateName(String aggregateName) {
        this.aggregateName = aggregateName;
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
    public List<DomainEvent> getDomainEvents() {
        var repo = Optional.ofNullable(repository).orElseGet(() -> (aggregateName, aggregateRootId) -> new ArrayList<>());
        var id = Optional.ofNullable(aggregateId).orElse(null);
        var name = Optional.ofNullable(aggregateName).orElse(null);
        return  Optional.ofNullable(domainEvents).orElse(repo.getEventsBy(name, id));
    }
}
