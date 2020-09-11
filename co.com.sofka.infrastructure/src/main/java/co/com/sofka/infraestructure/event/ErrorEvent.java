package co.com.sofka.infraestructure.event;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Error event.
 */
public class ErrorEvent {

    /**
     * The When.
     */
    public final Instant when;
    /**
     * The Uuid.
     */
    public final UUID uuid;
    /**
     * The Identify.
     */
    public final String identify;
    /**
     * The Error.
     */
    public final Throwable error;

    /**
     * Instantiates a new Error event.
     *
     * @param identify  the identify
     * @param throwable the throwable
     */
    public ErrorEvent(String identify, Throwable throwable) {
        this.identify = identify;
        this.error = throwable;
        this.when = Instant.now();
        this.uuid = UUID.randomUUID();
    }

}
