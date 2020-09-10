package co.com.sofka.business.sync;

import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.ResponseEvents;

import java.util.*;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * The type Use case executor.
 *
 * @param <T> the type parameter
 * @param <R> the type parameter
 */
public abstract class UseCaseExecutor<T, R extends UseCase.ResponseValues> implements Function<T, R> {
    private static final Logger logger = Logger.getLogger(UseCaseExecutor.class.getName());

    private DomainEventRepository repository;
    private String aggregateId;
    private UseCaseHandler useCaseHandler;
    private ServiceBuilder serviceBuilder;
    private Map<String, String> headers;
    private Set<UseCase.UseCaseWrap> useCases;

    /**
     * With service builder.
     *
     * @param serviceBuilder the service builder constructor
     * @return the use case executor
     */
    public UseCaseExecutor<T, R> withServiceBuilder(ServiceBuilder serviceBuilder) {
        this.serviceBuilder = Objects.requireNonNull(serviceBuilder);
        return this;
    }


    /**
     * With headers use case executor.
     *
     * @param headers the http or metadata
     * @return the use case executor
     */
    public UseCaseExecutor<T, R> withHeaders(Map<String, String> headers) {
        this.headers = Objects.requireNonNull(headers, "headers is required");
        return this;
    }

    /**
     * With domain event repo use case executor.
     *
     * @param repository the repository
     * @return the use case executor
     */
    public UseCaseExecutor<T, R> withDomainEventRepo(DomainEventRepository repository) {
        this.repository = Objects.requireNonNull(repository, "domain event repository is required");
        return this;
    }

    /**
     * With aggregate id use case executor.
     *
     * @param aggregateId the aggregate id
     * @return the use case executor
     */
    public UseCaseExecutor<T, R> withAggregateId(String aggregateId) {
        this.aggregateId = Objects.requireNonNull(aggregateId, "aggregate id required");
        return this;
    }

    /**
     * With use case handler use case executor.
     *
     * @param useCaseHandler the use case handler
     * @return the use case executor
     */
    public UseCaseExecutor<T, R> withUseCaseHandler(UseCaseHandler useCaseHandler) {
        this.useCaseHandler = Objects.requireNonNull(useCaseHandler, "handle for the executor use case is required");
        return this;
    }

    /**
     * With use cases case executor.
     *
     * @param useCase set
     * @return the use case executor
     */
    public UseCaseExecutor<T, R> withUseCases(Set<UseCase.UseCaseWrap> useCases) {
        this.useCases = Objects.requireNonNull(useCases, "use cases is required");
        return this;
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
     * Set Use Case Wrap
     * @return useCases
     */
    public Set<UseCase.UseCaseWrap> useCases() {
        return useCases;
    }


    /**
     * aggregate id
     * @return string id
     */
    public String aggregateId() {
        Objects.requireNonNull(aggregateId, "Aggregate identifier not available, consider using withAggregateId method");
        return aggregateId;
    }

    /**
     * Get Service Builder
     * @return serviceBuilder
     */
    public ServiceBuilder serviceBuilder() {
        return serviceBuilder;
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
     * Use case handler
     *
     * @return useCaseHandler
     */
    public UseCaseHandler useCaseHandler() {
        return Optional.ofNullable(useCaseHandler).orElse(UseCaseHandler.getInstance());
    }

    /**
     * Executor use case synchronously
     *
     *  @param useCase the use case
     * @param request the request for use case
     * @return request optional
     */
    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValues> Optional<R> runSynUseCase(UseCase<T, R> useCase, T request) {
        Optional.ofNullable(repository).ifPresentOrElse(useCase::addRepository, () ->
            logger.warning("No repository found for use case")
        );
        Optional.ofNullable(serviceBuilder).ifPresentOrElse(useCase::addServiceBuilder, () ->
                logger.warning("No service builder found for use case")
        );
        Optional.ofNullable(useCases).ifPresentOrElse(useCase::addUseCases, () ->
                logger.warning("No uses cases found for use case")
        );
        return useCaseHandler()
                .setIdentifyExecutor(aggregateId())
                .syncExecutor(useCase, request);
    }




}
