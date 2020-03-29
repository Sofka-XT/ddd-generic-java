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

    /**
     * Instantiates a new Command.
     *
     * @param type the type
     */
    protected Command(final String type) {
        this.type = type;
        this.uuid = UUID.randomUUID();
    }
}
