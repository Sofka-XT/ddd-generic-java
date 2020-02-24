package co.com.sofka.domain.generic;

import java.util.Objects;
import java.util.UUID;

public abstract class Entity<I> {
    protected I entityId;
    public Entity(I entityId){
        this.entityId = entityId;
    }

    public UUID generateIdentity(){
        return UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<?> entity = (Entity<?>) o;
        return Objects.equals(entityId, entity.entityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityId);
    }
}
