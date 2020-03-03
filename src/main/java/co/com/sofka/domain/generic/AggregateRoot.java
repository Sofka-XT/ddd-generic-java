package co.com.sofka.domain.generic;



import java.util.*;

import java.util.function.Consumer;

public abstract class AggregateRoot<T extends  AggregateRootId> extends Entity<T> {

    private final List<DomainEvent> changes = new LinkedList<>();
    private  final Set<Consumer<? super DomainEvent>> handleActions = new HashSet<>();

    public AggregateRoot(T aggregateRootId) {
        super(aggregateRootId);
    }
    public List<DomainEvent> getUncommittedChanges() {
        return List.copyOf(changes);
    }

    protected FunctionApply<AggregateRoot<T>> appendChange(DomainEvent event) {
        changes.add(event);
        return () -> {
            applyEvent(event);
            long version = currentVersionOf(event.type);
            event.nextVersionType(version);
            return this;
        };
    }

    protected final void registerEntityBehavior(EntityBehaviors<?> entityBehaviors){
        this.handleActions.addAll(entityBehaviors.behaviors);
    }

    protected void applyEvent(DomainEvent domainEvent){
        handleActions.forEach(consumer -> {
            try {
                consumer.accept(domainEvent);
            } catch (ClassCastException ignored) {
            }
        });
    }

    private long currentVersionOf(String eventType){
        return changes.stream().filter(event -> event.type.equals(eventType)).count();
    }

    public void markChangesAsCommitted() {
        changes.clear();
    }

    protected abstract static class EntityBehaviors<T>{
        protected T entity;
        protected Set<Consumer<? super DomainEvent>> behaviors = new HashSet<>();
        protected EntityBehaviors(T entity){
            this.entity = entity;
        }

        private Set<Consumer<? super DomainEvent>> behaviors() {
            return behaviors;
        }

        protected EntityBehaviors<T> add(Consumer<? extends DomainEvent> behavior){
            behaviors.add((Consumer<? super DomainEvent>)behavior);
            return this;
        }

    }

    @FunctionalInterface
    public interface FunctionApply<T> {
        T apply();
    }

}
