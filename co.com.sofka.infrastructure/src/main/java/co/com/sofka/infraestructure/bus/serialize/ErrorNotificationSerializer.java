package co.com.sofka.infraestructure.bus.serialize;

import co.com.sofka.infraestructure.AbstractSerializer;
import co.com.sofka.infraestructure.bus.notification.ErrorNotification;

public final class ErrorNotificationSerializer extends AbstractSerializer {

    private static ErrorNotificationSerializer eventSerializer;

    private ErrorNotificationSerializer() {
        super();
    }


    public static synchronized ErrorNotificationSerializer instance() {
        if (eventSerializer == null) {
            eventSerializer = new ErrorNotificationSerializer();
        }
        return eventSerializer;
    }


    public ErrorNotification deserialize(String aSerialization) {
        return gson.fromJson(aSerialization, ErrorNotification.class);
    }


    public String serialize(ErrorNotification object) {
        return gson.toJson(object);
    }

}