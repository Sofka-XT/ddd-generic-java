package co.com.sofka.domain.generic;


import java.util.List;

/**
 * The type Aggregate event.
 *
 * @param <T> the type parameter
 */
public abstract class AggregateEvent<T extends Identity> extends AggregateRoot<T> {


    private final BehaviorSubscriber behaviorSubscriber;

    /**
     * Instantiates a new Aggregate event.
     *
     * @param entityId the entity id
     */
    public AggregateEvent(T entityId) {
        super(entityId);
        behaviorSubscriber = new BehaviorSubscriber();
    }


    /**
     * Gets uncommitted changes.
     *
     * @return the uncommitted changes
     */
    public List<DomainEvent> getUncommittedChanges() {
        return List.copyOf(behaviorSubscriber.getChanges());
    }

    /**
     * Append change behavior subscriber . change apply.
     *
     * @param event the event
     * @return the behavior subscriber . change apply
     */
    protected BehaviorSubscriber.ChangeApply appendChange(DomainEvent event) {
        var nameClass = entityId.getClass().getSimpleName();
        var aggregate = nameClass.replaceAll("(Identity|Id)", "").toLowerCase();
        event.setAggregateName(aggregate);
        event.setAggregateRootId(entityId.value());
        return behaviorSubscriber.appendChange(event);
    }

    /**
     * Subscribe.
     *
     * @param eventBehavior the event behavior
     */
    protected final void subscribe(EventBehavior eventBehavior) {
        behaviorSubscriber.subscribe(eventBehavior);
    }

    /**
     * Apply event.
     *
     * @param domainEvent the domain event
     */
    protected void applyEvent(DomainEvent domainEvent) {
        behaviorSubscriber.applyEvent(domainEvent);
    }

    /**
     * Mark changes as committed.
     */
    public void markChangesAsCommitted() {
        behaviorSubscriber.getChanges().clear();
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
