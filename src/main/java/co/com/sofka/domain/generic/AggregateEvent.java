package co.com.sofka.domain.generic;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * The type Aggregate Event.
 *
 * @param <T> the type parameter
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public abstract class AggregateEvent<T extends AggregateRootId> extends AggregateRoot<T> {

    private final List<DomainEvent> changes = new LinkedList<>();
    private final Map<String, AtomicLong> versions = new ConcurrentHashMap<>();
    private final Set<Consumer<? super DomainEvent>> handleActions = new HashSet<>();

    /**
     * Instantiates a new Entity.
     *
     * @param entityId the entity id
     */
    public AggregateEvent(T entityId) {
        super(entityId);
    }


    /**
     * Gets uncommitted changes.
     *
     * @return the uncommitted changes
     */
    public List<DomainEvent> getUncommittedChanges() {
        return List.copyOf(changes);
    }

    /**
     * Append change function apply.
     *
     * @param event the event
     * @return the function apply
     */
    protected ChangeApply<AggregateEvent<T>> appendChange(DomainEvent event) {
        changes.add(event);
        return () -> {
            applyEvent(event);
            return this;
        };
    }

    /**
     * Register entity behavior.
     *
     * @param eventBehaviors the entity behaviors
     */
    protected final void registerEntityBehavior(EventBehaviors<?> eventBehaviors) {
        this.handleActions.addAll(eventBehaviors.behaviors);
    }

    /**
     * Apply event.
     *
     * @param domainEvent the domain event
     */
    protected void applyEvent(DomainEvent domainEvent) {
        handleActions.forEach(consumer -> {
            try {
                consumer.accept(domainEvent);
                var map = versions.get(domainEvent.type);
                long version = nextVersion(domainEvent, map);
                domainEvent.setVersionType(version);
            } catch (ClassCastException ignored) {
            }
        });
    }

    private long nextVersion(DomainEvent domainEvent, AtomicLong map) {
        if (map == null) {
            versions.put(domainEvent.type, new AtomicLong(domainEvent.versionType()));
            return domainEvent.versionType();
        }
        return versions.get(domainEvent.type).incrementAndGet();
    }


    /**
     * Mark changes as committed.
     */
    public void markChangesAsCommitted() {
        changes.clear();
    }

    /**
     * The interface Change apply.
     *
     * @param <T> the type parameter
     */
    @FunctionalInterface
    public interface ChangeApply<T> {
        /**
         * Apply t.
         *
         * @return the t
         */
        T apply();
    }

}
