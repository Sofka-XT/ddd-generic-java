package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Flow;

public class EventHandler {

    private static EventHandler instance;
   // private Map<String, UseCase<UseCase.ResponseEvents, UseCase.ResponseEvents>> useCases;
    private EventHandler() {
    }

    public static EventHandler getInstance() {
        if (instance == null) {
            instance = new EventHandler();
        }
        return instance;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseEvents> SimpleSubscriber execute(final UseCase<T, R> useCase, T values) {
        SimpleSubscriber subscriber = new SimpleSubscriber();

        return subscriber;
    }

    public static final class SimpleSubscriber implements  Flow.Subscriber<DomainEvent> {

        @Override
        public void onSubscribe(Flow.Subscription subscription) {

        }

        @Override
        public void onNext(DomainEvent domainEvent) {

        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    }
}