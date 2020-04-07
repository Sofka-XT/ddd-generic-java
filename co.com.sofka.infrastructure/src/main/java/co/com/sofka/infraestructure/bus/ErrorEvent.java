package co.com.sofka.infraestructure.bus;

import java.time.Instant;
import java.util.UUID;

/**
 * The type Error event.
 */
public class ErrorEvent {
    /**
     * The Code.
     */
    public final Integer code;
    /**
     * The Cause.
     */
    public final String cause;
    /**
     * The Reason.
     */
    public final String reason;

    /**
     * The When.
     */
    public final Instant when;
    /**
     * The Uuid.
     */
    public final UUID uuid;

    /**
     * Instantiates a new Error event.
     *
     * @param code   the code
     * @param cause  the cause
     * @param reason the reason
     */
    public ErrorEvent(Integer code, String cause, String reason) {
        this.code = code;
        this.cause = cause;
        this.reason = reason;
        this.when = Instant.now();
        this.uuid = UUID.randomUUID();
    }
}
