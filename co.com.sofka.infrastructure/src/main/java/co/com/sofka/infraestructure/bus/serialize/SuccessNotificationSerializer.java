package co.com.sofka.infraestructure.bus.serialize;

import co.com.sofka.infraestructure.AbstractSerializer;
import co.com.sofka.infraestructure.bus.notification.SuccessNotification;

/**
 * The type Success notification serializer.
 */
public final class SuccessNotificationSerializer extends AbstractSerializer {

    private static SuccessNotificationSerializer eventSerializer;

    private SuccessNotificationSerializer() {
        super();
    }


    /**
     * Instance success notification serializer.
     *
     * @return the success notification serializer
     */
    public static synchronized SuccessNotificationSerializer instance() {
        if (eventSerializer == null) {
            eventSerializer = new SuccessNotificationSerializer();
        }
        return eventSerializer;
    }


    /**
     * Deserialize success notification.
     *
     * @param aSerialization the a serialization
     * @return the success notification
     */
    public SuccessNotification deserialize(String aSerialization) {
        return gson.fromJson(aSerialization, SuccessNotification.class);
    }


    /**
     * Serialize string.
     *
     * @param object the object
     * @return the string
     */
    public String serialize(SuccessNotification object) {
        return gson.toJson(object);
    }

}