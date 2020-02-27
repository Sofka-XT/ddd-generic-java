package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.Flow;
import java.util.function.Consumer;

public abstract class UseCaseSub implements Flow.Subscriber<DomainEvent> {

    private Set<UseCase<UseCase.SubEvent, UseCase.ResponseValues>> useCases;
    private Queue<UseCase.ResponseValues> responseValues;


    public UseCaseSub(Set<UseCase<UseCase.SubEvent, UseCase.ResponseValues>> useCases) {
        this.useCases = useCases;
        this.responseValues = new ArrayDeque<>();
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
                                        .syncExecutor(useCase, () -> domainEvent);
                                responseValues.add(values);
                            }
                        }));
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onComplete() {
    }

    protected Queue<UseCase.ResponseValues> responseValues() {
        return responseValues;
    }


}


