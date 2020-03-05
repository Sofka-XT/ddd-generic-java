package co.com.sofka.domain.generic;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

/**
 * The type Aggregate root.
 *
 * @param <T> the type parameter
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public abstract class AggregateRoot<T extends AggregateRootId> extends Entity<T> {

    private final List<DomainEvent> changes = new LinkedList<>();
    private final Map<String, AtomicLong> versions = new ConcurrentHashMap<>();
    private final Set<Consumer<? super DomainEvent>> handleActions = new HashSet<>();

    /**
     * Instantiates a new Aggregate root.
     *
     * @param aggregateRootId the aggregate root id
     */
    public AggregateRoot(T aggregateRootId) {
        super(aggregateRootId);
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
    protected FunctionApply<AggregateRoot<T>> appendChange(DomainEvent event) {
        changes.add(event);
        return () -> {
            applyEvent(event);
            return this;
        };
    }

    /**
     * Register entity behavior.
     *
     * @param entityBehaviors the entity behaviors
     */
    protected final void registerEntityBehavior(EntityBehaviors<?> entityBehaviors) {
        this.handleActions.addAll(entityBehaviors.behaviors);
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
     * The interface Function apply.
     *
     * @param <T> the type parameter
     */
    @FunctionalInterface
    public interface FunctionApply<T> {
        /**
         * Apply t.
         *
         * @return the t
         */
        T apply();
    }

    /**
     * The type Entity behaviors.
     *
     * @param <T> the type parameter
     * @author Raul .A Alzate
     * @version 1.0
     * @since 2019 -03-01
     */
    protected abstract static class EntityBehaviors<T> {
        /**
         * The Entity.
         */
        protected T entity;
        /**
         * The Behaviors.
         */
        protected Set<Consumer<? super DomainEvent>> behaviors = new HashSet<>();

        /**
         * Instantiates a new Entity behaviors.
         *
         * @param entity the entity
         */
        protected EntityBehaviors(T entity) {
            this.entity = entity;
        }

        private Set<Consumer<? super DomainEvent>> behaviors() {
            return behaviors;
        }

        /**
         * Add entity behaviors.
         *
         * @param behavior the behavior
         * @return the entity behaviors
         */
        protected EntityBehaviors<T> add(Consumer<? extends DomainEvent> behavior) {
            behaviors.add((Consumer<? super DomainEvent>) behavior);
            return this;
        }

    }

}
