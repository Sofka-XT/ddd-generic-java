package co.com.sofka.domain.generic;

import java.util.Objects;
import java.util.UUID;

/**
 * The type Entity.
 *
 * @param <I> the type parameter
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public abstract class Entity<I> {
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
        this.entityId = entityId;
    }

    /**
     * Generate identity uuid.
     *
     * @return the uuid
     */
    public UUID generateIdentity() {
        return UUID.randomUUID();
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Entity<?> entity = (Entity<?>) object;
        return Objects.equals(entityId, entity.entityId);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(entityId);
    }
}
