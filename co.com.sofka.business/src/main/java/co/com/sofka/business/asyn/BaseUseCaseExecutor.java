package co.com.sofka.business.asyn;

import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.*;
import java.util.concurrent.Flow;


/**
 * The type Base use case executor.
 */
public abstract class BaseUseCaseExecutor {
    /**
     * The Subscriber event.
     */
    protected Flow.Subscriber<? super DomainEvent> subscriberEvent;
    /**
     * The Repository.
     */
    protected DomainEventRepository repository;
    /**
     * The Aggregate id.
     */
    protected String aggregateId;
    /**
     * The Use case handler.
     */
    protected UseCaseHandler useCaseHandler;
    /**
     * The Service builder.
     */
    protected ServiceBuilder serviceBuilder;
    /**
     * The Request.
     */
    protected UseCase.RequestValues request;
    /**
     * The Headers.
     */
    protected Map<String, String> headers;
    /**
     * The Use cases.
     */
    protected Set<UseCase.UseCaseWrap> useCases;

    /**
     * Subscriber event flow . subscriber.
     *
     * @return the flow . subscriber
     */
    public Flow.Subscriber<? super DomainEvent> subscriberEvent() {
        Objects.requireNonNull(subscriberEvent, "If the subscriber is not identified, consider using the withSubscriberEvent method");
        return subscriberEvent;
    }


    /**
     * Use case handler use case handler.
     *
     * @return the use case handler
     */
    public UseCaseHandler useCaseHandler() {
        return Optional.ofNullable(useCaseHandler).orElse(UseCaseHandler.getInstance());
    }

    /**
     * Repository domain event repository.
     *
     * @return the domain event repository
     */
    public DomainEventRepository repository() {
        return Objects.requireNonNull(repository, "No repository identified, consider using the withDomainEventRepo method");
    }

    /**
     * Aggregate id string.
     *
     * @return the string
     */
    public String aggregateId() {
        return Objects.requireNonNull(aggregateId, "Aggregate identifier not available, consider using withAggregateId method");
    }


    /**
     * Request use case . request values.
     *
     * @return the use case . request values
     */
    public UseCase.RequestValues request() {
        return request;
    }

    /**
     * Headers map.
     *
     * @return the map
     */
    public Map<String, String> headers() {
        return Optional.ofNullable(headers).orElse(new HashMap<>());
    }

    /**
     * Service builder service builder.
     *
     * @return the service builder
     */
    public ServiceBuilder serviceBuilder() {
        return serviceBuilder;
    }


    /**
     * Gets service.
     *
     * @param <T>   the type parameter
     * @param clasz the clasz
     * @return the service
     */
    public <T> Optional<T> getService(Class<T> clasz) {
        Objects.requireNonNull(serviceBuilder, "the service build cannot be null, you allow use the annotation ExtensionService");
        return serviceBuilder.getService(clasz);
    }

    /**
     * Use cases set.
     *
     * @return the set
     */
    public Set<UseCase.UseCaseWrap> useCases() {
        return useCases;
    }

    /**
     * With use cases base use case executor.
     *
     * @param useCases the use cases
     * @return the base use case executor
     */
    public BaseUseCaseExecutor withUseCases(Set<UseCase.UseCaseWrap> useCases) {
        this.useCases = Objects.requireNonNull(useCases, "use cases is required");
        return this;
    }

    /**
     * With subscriber event base use case executor.
     *
     * @param subscriberEvent the subscriber event
     * @return the base use case executor
     */
    public BaseUseCaseExecutor withSubscriberEvent(Flow.Subscriber<? super DomainEvent> subscriberEvent) {
        this.subscriberEvent = Objects.requireNonNull(subscriberEvent, "subscriber event is required");
        return this;
    }

    /**
     * With headers base use case executor.
     *
     * @param headers the headers
     * @return the base use case executor
     */
    public BaseUseCaseExecutor withHeaders(Map<String, String> headers) {
        this.headers = Objects.requireNonNull(headers, "headers is required");
        return this;
    }

    /**
     * With domain event repo base use case executor.
     *
     * @param repository the repository
     * @return the base use case executor
     */
    public BaseUseCaseExecutor withDomainEventRepo(DomainEventRepository repository) {
        this.repository = Objects.requireNonNull(repository, "domain event repository is required");
        return this;
    }

    /**
     * With aggregate id base use case executor.
     *
     * @param aggregateId the aggregate id
     * @return the base use case executor
     */
    public BaseUseCaseExecutor withAggregateId(String aggregateId) {
        this.aggregateId = Objects.requireNonNull(aggregateId, "aggregate id required");
        return this;
    }

    /**
     * With use case handler base use case executor.
     *
     * @param useCaseHandler the use case handler
     * @return the base use case executor
     */
    public BaseUseCaseExecutor withUseCaseHandler(UseCaseHandler useCaseHandler) {
        this.useCaseHandler = Objects.requireNonNull(useCaseHandler, "handle for the executor use case is required");
        return this;
    }

    /**
     * With service builder base use case executor.
     *
     * @param serviceBuilder the service builder
     * @return the base use case executor
     */
    public BaseUseCaseExecutor withServiceBuilder(ServiceBuilder serviceBuilder) {
        this.serviceBuilder = Objects.requireNonNull(serviceBuilder);
        return this;
    }
}
