package co.com.sofka.domain.generic;

import java.util.UUID;

public abstract class Entity<I> {
    protected I entityId;
    public Entity(I entityId){
        this.entityId = entityId;
    }

    public UUID generateIdentity(){
        return UUID.randomUUID();
    }
}
