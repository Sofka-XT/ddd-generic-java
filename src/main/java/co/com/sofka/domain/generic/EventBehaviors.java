package co.com.sofka.domain.generic;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

/**
 * The type Event behaviors.
 *
 * @param <T> the type parameter
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public abstract  class EventBehaviors<T> {
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
    protected EventBehaviors(T entity) {
        this.entity = entity;
    }

    /**
     * Add entity behaviors.
     *
     * @param behavior the behavior
     */
    protected void add(Consumer<? extends DomainEvent> behavior) {
        behaviors.add((Consumer<? super DomainEvent>) behavior);
    }

}
