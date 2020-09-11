package co.com.sofka.domain.generic;


import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The type Aggregate event.
 *
 * @param <T> the type parameter
 */
public abstract class AggregateEvent<T extends Identity> extends AggregateRoot<T> {


    private final ChangeEventSubscriber changeEventSubscriber;

    /**
     * Instantiates a new Aggregate event.
     *
     * @param entityId the entity id
     */
    public AggregateEvent(T entityId) {
        super(entityId);
        changeEventSubscriber = new ChangeEventSubscriber();
    }


    /**
     * Gets uncommitted changes.
     *
     * @return the uncommitted changes
     */
    public List<DomainEvent> getUncommittedChanges() {
        return List.copyOf(changeEventSubscriber.getChanges());
    }

    /**
     * Append change change event subscriber . change apply.
     *
     * @param event the event
     * @return the change event subscriber . change apply
     */
    protected ChangeEventSubscriber.ChangeApply appendChange(DomainEvent event) {
        var nameClass = entityId.getClass().getSimpleName();
        var aggregate = nameClass.replaceAll("(Identity|Id)", "").toLowerCase();
        event.setAggregateName(aggregate);
        event.setAggregateRootId(entityId.value());
        return changeEventSubscriber.appendChange(event);
    }

    /**
     * Subscribe.
     *
     * @param eventChange the event change
     */
    protected final void subscribe(EventChange eventChange) {
        changeEventSubscriber.subscribe(eventChange);
    }

    /**
     * Apply event.
     *
     * @param domainEvent the domain event
     */
    protected void applyEvent(DomainEvent domainEvent) {
        changeEventSubscriber.applyEvent(domainEvent);
    }

    /**
     * Mark changes as committed.
     */
    public void markChangesAsCommitted() {
        changeEventSubscriber.getChanges().clear();
    }

    /**
     * Refund event.
     */
    public void refundEvent() {
        changeEventSubscriber.getChanges().clear();
    }


    /**
     * Find event uncommitted optional.
     *
     * @param <E>   the type parameter
     * @param event the event
     * @return the optional
     */
    public <E extends DomainEvent> Optional<E> findEventUncommitted(Class<E> event) {
        return changeEventSubscriber.getChanges().stream()
                .filter(event::isInstance).map(e -> (E) e)
                .findFirst();
    }

    /**
     * Find all event uncommitted stream.
     *
     * @param <E>   the type parameter
     * @param event the event
     * @return the stream
     */
    public <E extends DomainEvent> Stream<E> findAllEventUncommitted(Class<E> event) {
        return changeEventSubscriber.getChanges().stream()
                .filter(event::isInstance).map(e -> (E) e);
    }

    @Override
    public boolean equals(Object object) {
        return super.equals(object);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
