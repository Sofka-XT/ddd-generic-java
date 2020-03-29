package co.com.sofka.domain.generic;

import java.io.Serializable;
import java.time.Instant;
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
    private Identity identity;
    private Long versionType;

    /**
     * Instantiates a new Domain event.
     *
     * @param type     the type
     * @param identity the identity
     */
    public DomainEvent(final String type, Identity identity) {
        this.type = type;
        this.identity = identity;
        this.when = Instant.now();
        this.uuid = UUID.randomUUID();
        this.versionType = 0L;
    }

    /**
     * Instantiates a new Domain event.
     *
     * @param type the type
     */
    public DomainEvent(final String type) {
        this(type, null);
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
     * Aggregate root id identity.
     *
     * @return the identity
     */
    public Identity aggregateRootId() {
        return identity;
    }

    /**
     * Set aggregate root id.
     *
     * @param identity the identity
     */
    public void setAggregateRootId(Identity identity) {
        this.identity = identity;
    }
}
