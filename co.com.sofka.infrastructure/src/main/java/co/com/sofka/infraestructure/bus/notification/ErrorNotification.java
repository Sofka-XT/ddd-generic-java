package co.com.sofka.infraestructure.bus.notification;

import co.com.sofka.infraestructure.DeserializeEventException;
import co.com.sofka.infraestructure.bus.serialize.ErrorNotificationSerializer;
import co.com.sofka.infraestructure.event.ErrorEvent;
import co.com.sofka.infraestructure.event.ErrorEventSerializer;

import java.util.Date;

/**
 * The type Error notification.
 */
public class ErrorNotification extends Notification {

    private ErrorNotification(String origin, String typeName, Date occurredOn, String body) {
        super(origin, typeName, occurredOn, body);
    }

    /**
     * Wrap event error notification.
     *
     * @param origin     the origin
     * @param errorEvent the error event
     * @return the error notification
     */
    public static ErrorNotification wrapEvent(String origin, ErrorEvent errorEvent) {
        return new ErrorNotification(origin, errorEvent.getClass().getCanonicalName(),
                new Date(errorEvent.when.toEpochMilli()),
                ErrorEventSerializer.instance().serialize(errorEvent)
        );
    }

    /**
     * Deserialize event error event.
     *
     * @return the error event
     */
    public ErrorEvent deserializeEvent() {
        try {
            return ErrorEventSerializer
                    .instance()
                    .deserialize(this.getBody(), Class.forName(this.getTypeName()));
        } catch (ClassNotFoundException e) {
            throw new DeserializeEventException(e.getCause());
        }
    }

    /**
     * To json string.
     *
     * @return the string
     */
    public String toJson() {
        return ErrorNotificationSerializer.instance().serialize(this);
    }
}
