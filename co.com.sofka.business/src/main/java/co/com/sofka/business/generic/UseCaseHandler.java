package co.com.sofka.business.generic;


import co.com.sofka.business.asyn.PublisherEvent;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.Optional;
import java.util.concurrent.Flow;

/**
 * The type Use case handler.
 */
public class UseCaseHandler {

    private static UseCaseHandler instance;
    private String identifyExecutor = null;

    private UseCaseHandler() {
    }

    /**
     * Gets instance.
     *
     * @return the instance
     */
    public static synchronized UseCaseHandler getInstance() {
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
    @SuppressWarnings("unchecked")
    public <T extends UseCase.RequestValues, R extends ResponseEvents> FunctionSubscriber asyncExecutor(
            final UseCase<T, R> useCase, T values) {
        return UseCaseReplyUtil.retry(() -> subscriber -> {
            try (PublisherEvent publisher = new PublisherEvent()) {
                publisher.subscribe(subscriber);
                useCase.setRequest(values);
                useCase.setIdentify(identifyExecutor());
                useCase.setUseCaseCallback((UseCase.UseCaseFormat<R>) publisher);
                useCase.run();
            }
        }, 5, ReplyBusinessException.class);
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
    @SuppressWarnings("unchecked")
    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValues> Optional<R> syncExecutor(
            final UseCase<T, R> useCase, T values) {
        return UseCaseReplyUtil.retry(() -> {
            UseCaseResponse<R> useCaseResponse = new UseCaseResponse<>();
            useCase.setRequest(values);
            useCase.setIdentify(identifyExecutor());
            useCase.setUseCaseCallback(useCaseResponse);
            useCase.run();
            if (useCaseResponse.hasError()) {
                throw useCaseResponse.exception;
            }
            return Optional.ofNullable(useCaseResponse.response);
        }, 5, ReplyBusinessException.class);

    }

    public String identifyExecutor() {
        return identifyExecutor;
    }

    public UseCaseHandler setIdentifyExecutor(String identifyExecutor) {
        this.identifyExecutor = identifyExecutor;
        return this;
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