package co.com.sofka.infraestructure.bus.notification;

import co.com.sofka.infraestructure.DeserializeEventException;
import co.com.sofka.infraestructure.bus.serialize.ErrorNotificationSerializer;
import co.com.sofka.infraestructure.event.ErrorEvent;
import co.com.sofka.infraestructure.event.ErrorEventSerializer;

import java.util.Date;

public class ErrorNotification extends Notification {

    private ErrorNotification(String origin, String typeName, Date occurredOn, String body) {
       super(origin, typeName, occurredOn, body);
    }

    public static ErrorNotification wrapEvent(String origin, ErrorEvent errorEvent) {
        return new ErrorNotification(origin, errorEvent.getClass().getCanonicalName(),
                new Date(errorEvent.when.toEpochMilli()),
                ErrorEventSerializer.instance().serialize(errorEvent)
        );
    }

    public ErrorEvent deserializeEvent() {
        try {
            return ErrorEventSerializer
                    .instance()
                    .deserialize(this.getBody(), Class.forName(this.getTypeName()));
        } catch (ClassNotFoundException e) {
            throw new DeserializeEventException(e.getCause());
        }
    }

    public String toJson() {
        return ErrorNotificationSerializer.instance().serialize(this);
    }
}
