package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.Optional;
import java.util.concurrent.Flow;

public class UseCaseHandler {

    private static UseCaseHandler instance;

    private UseCaseHandler() {
    }

    public static UseCaseHandler getInstance() {
        if (instance == null) {
            instance = new UseCaseHandler();
        }
        return instance;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseEvents> SimplePublisher execute(final UseCase<T, R> useCase, T values) {
        SimplePublisher publisher = new SimplePublisher();
        useCase.setRequestValues(values);
        useCase.setUseCaseCallback((UseCase.UseCaseFormat<R>) publisher);
        useCase.run();
        return publisher;
    }

    public static final class SimplePublisher implements  UseCase.UseCaseFormat<UseCase.ResponseEvents>, Flow.Publisher<DomainEvent> {

        private UseCase.ResponseEvents response;
        private RuntimeException exception;

        public SimplePublisher(){
            super();
        }

        @Override
        public void onSuccess(UseCase.ResponseEvents response) {
            this.response = response;
        }

        @Override
        public void onError(RuntimeException exception) {
            this.exception = exception;
        }

        @Override
        public void subscribe(Flow.Subscriber<? super DomainEvent> subscriber) {
            Optional.ofNullable(exception)
                    .ifPresentOrElse(subscriber::onError,
                            () -> response.getDomainEvents()
                                    .forEach(subscriber::onNext));
            subscriber.onComplete();
        }
    }
}