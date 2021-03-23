package co.com.sofka.infraestructure.asyn;


import co.com.sofka.business.generic.UnexpectedException;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.event.ErrorEvent;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.store.StoredEvent;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Flow;
import java.util.logging.Logger;


/**
 * The type Subscriber event.
 */
public class SubscriberEvent implements Flow.Subscriber<DomainEvent> {

    private static final Logger logger = Logger.getLogger(SubscriberEvent.class.getName());
    private final EventStoreRepository repository;
    private final EventBus eventBus;
    private Flow.Subscription subscription;

    /**
     * Instantiates a new Subscriber event.
     *
     * @param repository the repository
     * @param eventBus   the event bus
     */
    public SubscriberEvent(EventStoreRepository repository, EventBus eventBus) {
        this.repository = repository;
        this.eventBus = eventBus;
    }

    /**
     * Instantiates a new Subscriber event.
     *
     * @param repository the repository
     */
    public SubscriberEvent(EventStoreRepository repository) {
        this(repository, null);
    }

    /**
     * Instantiates a new Subscriber event.
     */
    public SubscriberEvent() {
        this(null, null);
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        subscription.request(100);
    }

    @Override
    public final void onNext(DomainEvent event) {
        Optional.ofNullable(eventBus).ifPresentOrElse(bus ->
                        bus.publish(event)
                , () ->
                        logger.warning("No EVENT BUS configured")
        );

        Optional.ofNullable(repository).ifPresentOrElse(repo -> {
                    StoredEvent storedEvent = StoredEvent.wrapEvent(event);
                    Optional.ofNullable(event.aggregateRootId()).ifPresent(aggregateId -> {
                        if (Objects.nonNull(event.getAggregateName()) && !event.getAggregateName().isBlank()) {
                            repo.saveEvent(event.getAggregateName(), aggregateId, storedEvent);
                        }
                    });
                }, () ->
                        logger.warning("No REPOSITORY configured")
        );
        subscription.request(100);
    }

    @Override
    public void onError(Throwable throwable) {
        Optional.ofNullable(eventBus).ifPresent(bus -> {
            var identify = ((UnexpectedException) throwable).getIdentify();
            var event = new ErrorEvent(identify, throwable);
            bus.publishError(event);
        });
        subscription.cancel();
    }

    @Override
    public void onComplete() {
    }

}
