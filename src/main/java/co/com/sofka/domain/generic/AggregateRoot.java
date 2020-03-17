package co.com.sofka.domain.generic;

/**
 * The type Aggregate root.
 *
 * @param <T> the type parameter
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public abstract class AggregateRoot<T extends AggregateRootId> extends Entity<T> {

    /**
     * The Version type.
     */
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
