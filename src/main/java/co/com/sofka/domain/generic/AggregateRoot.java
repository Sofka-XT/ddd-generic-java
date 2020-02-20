package co.com.sofka.domain.generic;



import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AggregateRoot {
    private final List<DomainEvent> changes;
    private final List<Consumer<? super DomainEvent>> handleActions;

    private AggregateRootId aggregateRootId;

    public AggregateRoot(AggregateRootId aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
        changes = new LinkedList<>();
        handleActions = new LinkedList<>();
    }
    public List<DomainEvent> getUncommittedChanges() {
        return changes;
    }

    public Function<Consumer<? extends DomainEvent>, AggregateRoot> appendChange(DomainEvent event) {
        changes.add(event);
        return action -> {
            ((Consumer)action).accept(event);
            long version = changes.stream().filter(e -> e.type.equals(event.type)).count();
            event.nextVersionType(version);
            return this;
        };
    }

    @SafeVarargs
    public final void registerActions(Consumer<? extends DomainEvent> ...actions){
        for(Consumer<? extends DomainEvent> consumer : actions){
                handleActions.add((Consumer<? super DomainEvent>) consumer);
        }
    }

    public void applyEvent(DomainEvent domainEvent){
        handleActions.forEach(consumer -> {
            try {
                consumer.accept(domainEvent);
            } catch (ClassCastException ignored) {
            }
        });
    }

    public void markChangesAsCommitted() {
        changes.clear();
    }

    public AggregateRootId aggregateId() {
        return aggregateRootId;
    }

}
