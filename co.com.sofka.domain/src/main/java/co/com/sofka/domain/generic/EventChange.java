package co.com.sofka.domain.generic;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The type Event change.
 */
public abstract class EventChange {

    /**
     * The Behaviors.
     */
    protected Set<Consumer<? super DomainEvent>> behaviors = new HashSet<>();

    /**
     * Apply.
     *
     * @param changeEvent the change event
     */
    protected void apply(Consumer<? extends DomainEvent> changeEvent) {
        behaviors.add((Consumer<? super DomainEvent>) changeEvent);
    }
}
