package co.com.sofka.domain.generic;

/**
 * The type Aggregate root.
 *
 * @param <T> the type parameter
 */
public abstract class AggregateRoot<T extends Identity> extends Entity<T> {

    /**
     * The Version type.
     */
    protected Long versionType;

    /**
     * Instantiates a new Aggregate root.
     *
     * @param entityId the entity id
     */
    public AggregateRoot(T entityId) {
        super(entityId);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
