package co.com.sofka.domain.generic;

public abstract class AggregateRoot<T extends AggregateRootId> extends Entity<T> {

    protected Long versionType;
    /**
     * Instantiates a new Entity.
     *
     * @param entityId the entity id
     */
    public AggregateRoot(T entityId) {
        super(entityId);
    }
}
