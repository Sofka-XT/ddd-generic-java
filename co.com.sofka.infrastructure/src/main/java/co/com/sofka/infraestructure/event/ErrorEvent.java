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

    public final Throwable error;


    /**
     * Instantiates a new Error event.
     */
    public ErrorEvent(Throwable throwable) {
        this.error = throwable;
        this.when = Instant.now();
        this.uuid = UUID.randomUUID();
    }

}
