package co.com.sofka.business.sync;

import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;

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
     * With service builder use case executor.
     *
     * @param serviceBuilder the service builder
     * @return the use case executor
     */
    public UseCaseExecutor<T, R> withServiceBuilder(ServiceBuilder serviceBuilder) {
        this.serviceBuilder = Objects.requireNonNull(serviceBuilder);
        return this;
    }


    /**
     * With headers use case executor.
     *
     * @param headers the headers
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
     * With use cases use case executor.
     *
     * @param useCases the use cases
     * @return the use case executor
     */
    public UseCaseExecutor<T, R> withUseCases(Set<UseCase.UseCaseWrap> useCases) {
        this.useCases = Objects.requireNonNull(useCases, "use cases is required");
        return this;
    }

    /**
     * Headers map.
     *
     * @return the map
     */
    public Map<String, String> headers(){
        return Optional.ofNullable(headers).orElse(new HashMap<>());
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
     * Aggregate id string.
     *
     * @return the string
     */
    public String aggregateId() {
        Objects.requireNonNull(aggregateId, "Aggregate identifier not available, consider using withAggregateId method");
        return aggregateId;
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
     * Repository domain event repository.
     *
     * @return the domain event repository
     */
    public DomainEventRepository repository() {
        Objects.requireNonNull(repository, "No repository identified, consider using the withDomainEventRepo method");
        return repository;
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
     * Run syn use case optional.
     *
     * @param <T>     the type parameter
     * @param <R>     the type parameter
     * @param useCase the use case
     * @param request the request
     * @return the optional
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
