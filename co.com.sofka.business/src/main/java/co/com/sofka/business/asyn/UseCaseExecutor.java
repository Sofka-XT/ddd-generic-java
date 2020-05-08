package co.com.sofka.business.asyn;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

/**
 * The type Use case executor.
 */
public abstract class UseCaseExecutor implements Consumer<Map<String, String>> {
    private Flow.Subscriber<? super DomainEvent> subscriberEvent;
    private DomainEventRepository repository;
    private String aggregateId;
    private UseCaseHandler useCaseHandler;

    public abstract void run();

    /**
     * Subscriber event flow subscriber.
     *
     * @return the flow subscriber
     */
    public Flow.Subscriber<? super DomainEvent> subscriberEvent() {
        Objects.requireNonNull(subscriberEvent, "If the subscriber is not identified, consider using the withSubscriberEvent method");
        return subscriberEvent;
    }


    /**
     * Use case handler
     *
     * @return useCaseHandler
     */
    public UseCaseHandler useCaseHandler() {
        Objects.requireNonNull(useCaseHandler, "There is no handle for this execution, consider using the withUseCaseHandler method");
        return useCaseHandler;
    }

    /**
     * Domain event repository
     *
     * @return the repository
     */
    public DomainEventRepository repository() {
        Objects.requireNonNull(repository, "No repository identified, consider using the withDomainEventRepo method");
        return repository;
    }

    /**
     * String the aggregate root id
     *
     * @return the aggregate id
     */
    public String aggregateId() {
        Objects.requireNonNull(aggregateId, "Aggregate identifier not available, consider using withAggregateId method");
        return aggregateId;
    }


    /**
     * With subscriber event use case executor.
     *
     * @param subscriberEvent the subscriber event
     * @return the use case executor
     */
    public UseCaseExecutor withSubscriberEvent(Flow.Subscriber<? super DomainEvent> subscriberEvent) {
        this.subscriberEvent = Objects.requireNonNull(subscriberEvent, "subscriber event is required");
        return this;
    }


    /**
     * With domain event repo use case executor.
     *
     * @param repository the repository
     * @return the use case executor
     */
    public UseCaseExecutor withDomainEventRepo(DomainEventRepository repository) {
        this.repository = Objects.requireNonNull(repository, "domain event repository is required");
        return this;
    }

    /**
     * With aggregate id use case executor.
     *
     * @param aggregateId the aggregate id
     * @return the use case executor
     */
    public UseCaseExecutor withAggregateId(String aggregateId) {
        this.aggregateId = Objects.requireNonNull(aggregateId, "aggregate id required");
        return this;
    }

    /**
     * With use case handler use case executor.
     *
     * @param useCaseHandler the use case handler
     * @return the use case executor
     */
    public UseCaseExecutor withUseCaseHandler(UseCaseHandler useCaseHandler) {
        this.useCaseHandler = Objects.requireNonNull(useCaseHandler, "handle for the executor use case is required");
        return this;
    }

    /**
     * Executor use case
     *
     * @param useCase the use case
     * @param request the request for use case
     */
    public <T extends UseCase.RequestValues, R extends ResponseEvents> void runUseCase(UseCase<T, R> useCase, T request) {
        useCaseHandler().asyncExecutor(useCase, request).subscribe(subscriberEvent());
    }
}
