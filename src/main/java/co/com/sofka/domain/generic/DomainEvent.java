package co.com.sofka.domain.generic;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Domain event.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public abstract class DomainEvent {
    /**
     * The When.
     */
    public final Instant when;
    /**
     * The Uuid.
     */
    public final UUID uuid;
    /**
     * The Type.
     */
    public final String type;
    /**
     * The Aggregate root id.
     */
    public final AggregateRootId aggregateRootId;
    private Long versionType;

    /**
     * Instantiates a new Domain event.
     *
     * @param type            the type
     * @param aggregateRootId the aggregate root id
     */
    protected DomainEvent(final String type, AggregateRootId aggregateRootId) {
        this.type = type;
        this.aggregateRootId = aggregateRootId;
        this.when = Instant.now();
        this.uuid = UUID.randomUUID();
        this.versionType = 0L;
    }

    /**
     * Version type long.
     *
     * @return the long
     */
    public Long versionType() {
        return versionType;
    }

    /**
     * Sets version type.
     *
     * @param versionType the version type
     */
    public void setVersionType(Long versionType) {
        this.versionType = versionType;
    }

    /**
     * Aggregate root id aggregate root id.
     *
     * @return the aggregate root id
     */
    public AggregateRootId aggregateRootId() {
        return aggregateRootId;
    }
}
