package co.com.sofka.domain.generic;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The type Event behavior.
 */
public abstract class EventChange {

    /**
     * The Behaviors.
     */
    protected Set<Consumer<? super DomainEvent>> behaviors = new HashSet<>();

    /**
     * Give.
     *
     * @param changeEvent the behavior
     */
    protected void apply(Consumer<? extends DomainEvent> changeEvent) {
        behaviors.add((Consumer<? super DomainEvent>) changeEvent);
    }
}
