package co.com.sofka.domain.generic;

import java.io.Serializable;
import java.util.UUID;

/**
 * The type Command.
 */
public abstract class Command implements Serializable {
    /**
     * The Uuid.
     */
    public final UUID uuid;
    /**
     * The Type.
     */
    public final String type;


    private transient Identity aggregateRootId;

    /**
     * Instantiates a new Command.
     *
     * @param type the type
     */
    public Command(final String type) {
        this.type = type;
        this.uuid = UUID.randomUUID();
    }

    /**
     * Gets aggregate root id.
     *
     * @return the aggregate root id
     */
    public Identity getAggregateRootId() {
        return aggregateRootId;
    }

    /**
     * Sets aggregate root id.
     *
     * @param aggregateRootId the aggregate root id
     */
    public void setAggregateRootId(Identity aggregateRootId) {
        this.aggregateRootId = aggregateRootId;
    }

}
