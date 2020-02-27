package co.com.sofka.domain.generic;



import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AggregateRoot<T extends  AggregateRootId> extends Entity<T> {
    private final List<DomainEvent> changes;
    private final List<Consumer<? super DomainEvent>> handleActions;

    public AggregateRoot(T aggregateRootId) {
        super(aggregateRootId);
        changes = new LinkedList<>();
        handleActions = new LinkedList<>();
    }
    public List<DomainEvent> getUncommittedChanges() {
        return List.copyOf(changes);
    }

    protected Function<Consumer<? extends DomainEvent>, AggregateRoot<T>> appendChange(DomainEvent event) {
        changes.add(event);
        return action -> {
            ((Consumer)action).accept(event);
            long version = currentVersionOf(event.type);
            event.nextVersionType(version);
            return this;
        };
    }

    @SafeVarargs
    protected final void registerActions(Consumer<? extends DomainEvent> ...actions){
        for(Consumer<? extends DomainEvent> consumer : actions){
                handleActions.add((Consumer<? super DomainEvent>) consumer);
        }
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

}
