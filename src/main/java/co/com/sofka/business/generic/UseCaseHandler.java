package co.com.sofka.business.generic;


import co.com.sofka.business.asyn.PublisherEvent;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.Optional;
import java.util.concurrent.Flow;

/**
 * The type Use case handler.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public class UseCaseHandler {

    private static UseCaseHandler instance;

    private UseCaseHandler() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static UseCaseHandler getInstance() {
        if (instance == null) {
            instance = new UseCaseHandler();
        }
        return instance;
    }

    /**
     * Async executor function subscriber.
     *
     * @param <T>     the type parameter
     * @param <R>     the type parameter
     * @param useCase the use case
     * @param values  the values
     * @return the function subscriber
     */
    public <T extends UseCase.RequestValues, R extends ResponseEvents> FunctionSubscriber asyncExecutor(
            final UseCase<T, R> useCase, T values) {
        return subscriber -> {
            try (PublisherEvent publisher = new PublisherEvent()) {
                publisher.subscribe(subscriber);
                useCase.setRequest(values);
                useCase.setUseCaseCallback((UseCase.UseCaseFormat<R>) publisher);
                useCase.run();
            }
        };
    }

    /**
     * Sync executor optional.
     *
     * @param <T>     the type parameter
     * @param <R>     the type parameter
     * @param useCase the use case
     * @param values  the values
     * @return the optional
     */
    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValues> Optional<R> syncExecutor(
            final UseCase<T, R> useCase, T values) {

        UseCaseResponse<R> useCaseResponse = new UseCaseResponse<>();
        useCase.setRequest(values);
        useCase.setUseCaseCallback(useCaseResponse);
        useCase.run();
        if (useCaseResponse.hasError()) {
            throw useCaseResponse.exception;
        }
        return Optional.of(useCaseResponse.response);
    }

    /**
     * The interface Function subscriber.
     */
    @FunctionalInterface
    public interface FunctionSubscriber {
        /**
         * Subscribe.
         *
         * @param subscriber the subscriber
         */
        void subscribe(Flow.Subscriber<? super DomainEvent> subscriber);
    }
}