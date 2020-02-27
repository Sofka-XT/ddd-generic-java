package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.Optional;
import java.util.concurrent.Flow;

public final class SimplePublisher implements  UseCase.UseCaseFormat<UseCase.PubEvents>, Flow.Publisher<DomainEvent> {

    private UseCase.PubEvents response;
    private RuntimeException exception;

    public SimplePublisher(){
        super();
    }

    @Override
    public void onSuccess(UseCase.PubEvents response) {
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
