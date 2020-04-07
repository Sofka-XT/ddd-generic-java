package co.com.sofka.infraestructure.asyn;


import co.com.sofka.business.asyn.ListenerEvent;
import co.com.sofka.domain.generic.Identity;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.ErrorEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.store.StoredEvent;


import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Flow;


/**
 * The type Subscriber event.
 *
 * @param <T> the type parameter
 */
public class SubscriberEvent<T extends Identity> implements Flow.Subscriber<DomainEvent> {

    private final EventStoreRepository<T> repository;
    private final EventBus eventBus;
    private final ListenerEvent listenerEvent;
    private Flow.Subscription subscription;

    /**
     * Instantiates a new Subscriber event.
     *
     * @param repository    the repository
     * @param eventBus      the event bus
     * @param listenerEvent the listener event
     */
    public SubscriberEvent(EventStoreRepository<T> repository, EventBus eventBus, ListenerEvent listenerEvent) {
        this.repository = repository;
        this.eventBus = eventBus;
        this.listenerEvent = listenerEvent;
    }


    /**
     * Instantiates a new Subscriber event.
     *
     * @param repository the repository
     * @param eventBus   the event bus
     */
    public SubscriberEvent(EventStoreRepository<T> repository, EventBus eventBus) {
        this(repository, eventBus, null);
    }


    /**
     * Instantiates a new Subscriber event.
     *
     * @param repository the repository
     */
    public SubscriberEvent(EventStoreRepository<T> repository) {
        this(repository, null, null);
    }

    /**
     * Instantiates a new Subscriber event.
     */
    public SubscriberEvent() {
        this(null, null, null);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(1);
        Optional.ofNullable(listenerEvent).ifPresent(listener ->
                listener.onSubscribe(subscription)
        );
    }

    @Override
    public final void onNext(DomainEvent event) {
        Optional.ofNullable(eventBus).ifPresent(bus -> bus.publish(event));
        Optional.ofNullable(repository).ifPresent(repo -> {
            StoredEvent storedEvent = StoredEvent.wrapEvent(event);
            repo.saveEvent((T) event.aggregateRootId(), storedEvent);
        });
        Optional.ofNullable(listenerEvent).ifPresent(listener -> {
            listener.setSubscriber(this);
            listener.onNext(event);
        });
        subscription.request(1);
    }

    @Override
    public void onError(Throwable throwable) {
        var cause = Optional.ofNullable(throwable.getCause())
                .map(c -> Arrays.toString(c.getStackTrace())
                        .substring(0, 250))
                .orElse("");
        Optional.ofNullable(eventBus).ifPresent(bus -> bus.publishError(new ErrorEvent(
                504, cause,
                throwable.getMessage())
        ));
        Optional.ofNullable(listenerEvent).ifPresent(listener ->
                listener.onError(throwable)
        );
        subscription.cancel();
    }

    @Override
    public void onComplete() {
        Optional.ofNullable(listenerEvent).ifPresent(ListenerEvent::onComplete);
    }
}
