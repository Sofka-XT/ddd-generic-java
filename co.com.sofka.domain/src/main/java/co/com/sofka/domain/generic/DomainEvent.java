package co.com.sofka.domain.generic;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
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
    private String identity;
    private String aggregate;
    private Long versionType;

    /**
     * Instantiates a new Domain event.
     *
     * @param type     the type
     * @param identity the identity
     */
    public DomainEvent(final String type, String identity) {
        this.type = type;
        this.identity = identity;
        this.aggregate = "default";
        this.when = Instant.now();
        this.uuid = UUID.randomUUID();
        this.versionType = 1L;
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
    public String aggregateRootId() {
        return identity;
    }

    /**
     * Sets aggregate root id.
     *
     * @param identity the identity
     */
    public void setAggregateRootId(String identity) {
        this.identity = Objects.requireNonNull(identity, "The identity cannot be a value null");
    }

    /**
     * set aggregate name
     *
     * @param aggregate the aggregate
     */
    public void setAggregateName(String aggregate){
        this.aggregate = aggregate;
    }

    /**
     * get aggregate name
     *
     * @return the aggregate name
     */
    public String getAggregate(){
        return aggregate;
    }
}
