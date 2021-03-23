package co.com.sofka.domain.generic;


import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * The type Command.
 */
public class Command implements Serializable {

    private final long when;
    private final String uuid;


    /**
     * Instantiates a new Command.
     */
    public Command() {
        this.uuid = UUID.randomUUID().toString();
        this.when = Instant.now().toEpochMilli();
    }

    /**
     * When long.
     *
     * @return the long
     */
    public long when() {
        return when;
    }

    /**
     * Uuid string.
     *
     * @return the string
     */
    public String uuid() {
        return uuid;
    }
}
