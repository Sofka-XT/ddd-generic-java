package co.com.sofka.domain.generic;

import java.util.Objects;

/**
 * The type Entity.
 *
 * @param <I> the type parameter
 */
public abstract class Entity<I extends Identity> {
    /**
     * The Entity id.
     */
    protected I entityId;

    /**
     * Instantiates a new Entity.
     *
     * @param entityId the entity id
     */
    public Entity(I entityId) {
        this.entityId = Objects.requireNonNull(entityId, "The identity cannot be a value null");
    }

    /**
     * Identity .
     *
     * @return the
     */
    public I identity() {
        return entityId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Entity<?> entity = (Entity<?>) object;
        return entityId.value().equals(entity.entityId.value());
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId);
    }
}
