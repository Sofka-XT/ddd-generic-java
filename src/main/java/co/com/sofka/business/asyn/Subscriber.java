package co.com.sofka.business.asyn;

import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.ErrorEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.store.StoredEvent;

import java.util.Optional;
import java.util.concurrent.Flow;


/**
 * The type Subscriber.
 *
 * @param <T> the type parameter
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public class Subscriber<T extends AggregateRootId> implements Flow.Subscriber<DomainEvent> {

    private final EventStoreRepository<T> repository;
    private final EventBus eventBus;
    private Flow.Subscription subscription;

    /**
     * Instantiates a new Subscriber.
     *
     * @param repository the repository
     * @param eventBus   the event bus
     */
    public Subscriber(EventStoreRepository<T> repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    /**
     * On subscribe.
     *
     * @param subscription the subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
    }

    /**
     * On next.
     *
     * @param event the event
     */
    @Override
    public final void onNext(DomainEvent event) {
        Optional.of(eventBus).ifPresent(bus -> bus.publish(event));
        Optional.of(repository).ifPresent(repo -> {
            StoredEvent storedEvent = StoredEvent.wrapEvent(event);
            repo.saveEvent((T) event.aggregateRootId, storedEvent);
        });
        subscription.request(1);
    }

    /**
     * On error.
     *
     * @param throwable the throwable
     */
    @Override
    public void onError(Throwable throwable) {
        Optional.of(eventBus).ifPresent(bus -> bus.publishError(new ErrorEvent(
                504, "There is a problem saving the event",
                throwable.getMessage())
        ));
        subscription.cancel();
    }

    /**
     * On complete.
     */
    @Override
    public void onComplete() {

    }
}
