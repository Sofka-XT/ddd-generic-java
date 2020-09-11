package co.com.sofka.domain.generic;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

/**
 * The type Domain event.
 */
public abstract class DomainEvent implements Serializable {
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
    private String aggregateRootId;
    private String aggregate;
    private Long versionType;

    /**
     * Instantiates a new Domain event.
     *
     * @param type            the type
     * @param aggregateRootId the aggregate root id
     * @param uuid            the uuid
     */
    public DomainEvent(final String type, String aggregateRootId, UUID uuid) {
        this.type = type;
        this.aggregateRootId = aggregateRootId;
        this.aggregate = "default";
        this.when = Instant.now();
        this.uuid = uuid;
        this.versionType = 1L;
    }


    /**
     * Instantiates a new Domain event.
     *
     * @param type the type
     * @param uuid the uuid
     */
    public DomainEvent(final String type, UUID uuid) {
        this(type, null, uuid);
    }

    /**
     * Instantiates a new Domain event.
     *
     * @param type the type
     */
    public DomainEvent(final String type) {
        this(type, null, UUID.randomUUID());
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
     * Aggregate root id string.
     *
     * @return the string
     */
    public String aggregateRootId() {
        return aggregateRootId;
    }

    /**
     * Sets aggregate root id.
     *
     * @param aggregateRootId the aggregate root id
     */
    public void setAggregateRootId(String aggregateRootId) {
        this.aggregateRootId = Objects.requireNonNull(aggregateRootId, "The aggregateRootId cannot be a value null");
    }

    /**
     * Gets aggregate name.
     *
     * @return the aggregate name
     */
    public String getAggregateName() {
        return aggregate;
    }

    /**
     * Sets aggregate name.
     *
     * @param aggregate the aggregate
     */
    public void setAggregateName(String aggregate) {
        this.aggregate = aggregate;
    }
}
