package co.com.sofka.infraestructure.event;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Error event.
 */
public class ErrorEvent {

    public final Instant when;
    public final UUID uuid;
    public final String identify;
    public final Throwable error;

    /**
     * Instantiates a new Error event.
     */
    public ErrorEvent(String identify, Throwable throwable) {
        this.identify = identify;
        this.error = throwable;
        this.when = Instant.now();
        this.uuid = UUID.randomUUID();
    }

}
