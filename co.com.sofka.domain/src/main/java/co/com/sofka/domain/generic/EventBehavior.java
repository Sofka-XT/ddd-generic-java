package co.com.sofka.domain.generic;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The type Event behavior.
 */
public abstract class EventBehavior {

    /**
     * The Behaviors.
     */
    protected Set<Consumer<? super DomainEvent>> behaviors = new HashSet<>();

    /**
     * Give.
     *
     * @param behavior the behavior
     */
    protected void give(Consumer<? extends DomainEvent> behavior) {
        behaviors.add((Consumer<? super DomainEvent>) behavior);
    }
}
