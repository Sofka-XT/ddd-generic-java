package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Flow;

public abstract class UseCaseSubPub implements Flow.Subscriber<DomainEvent> {

    private Set<UseCase<UseCase.SubEvent, UseCase.PubEvents>> useCases;
    private Queue<SimplePublisher> publishers;


    public UseCaseSubPub(Set<UseCase<UseCase.SubEvent, UseCase.PubEvents>> useCases) {
        this.useCases = useCases;
        this.publishers = new ArrayDeque<>();
    }


    @Override
    public void onSubscribe(Flow.Subscription subscription) {
    }

    @Override
    public final void onNext(DomainEvent domainEvent) {
        useCases.forEach(useCase ->
                Optional.of(useCase.request().getDomainEvent())
                        .ifPresent(event -> {
                            if (event.getClass().isInstance(domainEvent)) {
                                var values = UseCaseHandler.getInstance()
                                        .asyncExecutor(useCase, () -> domainEvent);
                                publishers.add(values);
                            }
                        }));
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
    }

    protected Queue<SimplePublisher> publishers() {
        return publishers;
    }


}


