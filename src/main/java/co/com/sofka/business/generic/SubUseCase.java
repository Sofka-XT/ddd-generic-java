package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.store.StoredEvent;

import java.util.concurrent.Flow;

public class SubUseCase<T extends AggregateRootId> implements Flow.Subscriber<DomainEvent> {

    private final EventStoreRepository<T> repository;
    private final EventBus eventBus;

    public SubUseCase(EventStoreRepository<T> repository, EventBus eventBus ){
        this.repository = repository;
        this.eventBus = eventBus;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public final void onNext(DomainEvent event) {
        eventBus.publish(event);
        StoredEvent storedEvent = StoredEvent.wrapEvent(event);
        repository.saveEvent((T)event.aggregateRootId, storedEvent);
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
