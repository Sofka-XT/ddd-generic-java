package co.com.sofka.infraestructure.bus.serialize;

import co.com.sofka.infraestructure.AbstractSerializer;
import co.com.sofka.infraestructure.bus.notification.SuccessNotification;

public final class SuccessNotificationSerializer extends AbstractSerializer {

    private static SuccessNotificationSerializer eventSerializer;

    private SuccessNotificationSerializer() {
        super();
    }


    public static synchronized SuccessNotificationSerializer instance() {
        if (eventSerializer == null) {
            eventSerializer = new SuccessNotificationSerializer();
        }
        return eventSerializer;
    }


    public SuccessNotification deserialize(String aSerialization) {
        return gson.fromJson(aSerialization, SuccessNotification.class);
    }


    public String serialize(SuccessNotification object) {
        return gson.toJson(object);
    }

}