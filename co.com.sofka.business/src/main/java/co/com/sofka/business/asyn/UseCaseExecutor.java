package co.com.sofka.business.asyn;

import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.*;
import java.util.concurrent.Flow;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * The type Use case executor.
 */
public abstract class UseCaseExecutor implements Consumer<Map<String, String>> {
    private static final Logger logger = Logger.getLogger(UseCaseExecutor.class.getName());
    private Flow.Subscriber<? super DomainEvent> subscriberEvent;
    private DomainEventRepository repository;
    private String aggregateId;
    private UseCaseHandler useCaseHandler;
    private ServiceBuilder serviceBuilder;
    private UseCase.RequestValues request;
    private Map<String, String> headers;
    private Set<UseCase.UseCaseWrap> useCases;

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
        return Optional.ofNullable(useCaseHandler).orElse(UseCaseHandler.getInstance());
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
     * Get useCase request value
     *
     * @return request value
     */
    public UseCase.RequestValues request() {
        return request;
    }

    /**
     * Get headers configured
     *
     * @return headers values
     */
    public Map<String, String> headers(){
        return Optional.ofNullable(headers).orElse(new HashMap<>());
    }

    /**
     * Get Service Builder
     * @return serviceBuilder
     */
    public ServiceBuilder serviceBuilder() {
        return serviceBuilder;
    }


    /**
     * Get service registered
     *
     * @param class for find service
     * @return serviceClass
     */
    public <T> Optional<T> getService(Class<T> clasz) {
        Objects.requireNonNull(serviceBuilder, "the service build cannot be null, you allow use the annotation ExtensionService");
        return serviceBuilder.getService(clasz);
    }

    /**
     * Set Use Case Wrap
     * @return useCases
     */
    public Set<UseCase.UseCaseWrap> useCases() {
        return useCases;
    }

    /**
     * With use cases case executor.
     *
     * @param useCase set
     * @return the use case executor
     */
    public UseCaseExecutor withUseCases(Set<UseCase.UseCaseWrap> useCases) {
        this.useCases = Objects.requireNonNull(useCases, "use cases is required");
        return this;
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
     * With headers use case executor.
     *
     * @param headers the http or metadata
     * @return the use case executor
     */
    public UseCaseExecutor withHeaders(Map<String, String> headers) {
        this.headers = Objects.requireNonNull(headers, "headers is required");
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
     * With service builder.
     *
     * @param serviceBuilder the service builder constructor
     * @return the use case executor
     */
    public UseCaseExecutor withServiceBuilder(ServiceBuilder serviceBuilder) {
        this.serviceBuilder = Objects.requireNonNull(serviceBuilder);
        return this;
    }

    /**
     * executor use case
     * @param args map of the arguments
     * @param headers map of the headers
     */
    public void executor(Map<String, String> args, Map<String, String> headers){
        withHeaders(headers).accept(args);
        run();
    }

    /**
     * executor use case
     * @param args map of the arguments
     */
    public void executor(Map<String, String> args){
        accept(args);
        run();
    }

    /**
     * Executor use case asynchronously
     *
     * @param useCase the use case
     * @param request the request for use case
     */
    public <T extends UseCase.RequestValues, R extends ResponseEvents> void runUseCase(UseCase<T, R> useCase, T request) {
        this.request = request;
        Optional.ofNullable(repository).ifPresentOrElse(useCase::addRepository, () ->
                logger.warning("No repository found for use case")
        );
        Optional.ofNullable(serviceBuilder).ifPresentOrElse(useCase::addServiceBuilder, () ->
                logger.warning("No service builder found for use case")
        );
        Optional.ofNullable(useCases).ifPresentOrElse(useCase::addUseCases, () ->
                logger.warning("No uses cases found for use case")
        );
        useCaseHandler()
                .setIdentifyExecutor(aggregateId())
                .asyncExecutor(useCase, request)
                .subscribe(subscriberEvent());
    }
}
