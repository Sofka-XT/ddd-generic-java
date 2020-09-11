package co.com.sofka.infraestructure.bus.serialize;

import co.com.sofka.infraestructure.AbstractSerializer;
import co.com.sofka.infraestructure.bus.notification.ErrorNotification;

/**
 * The type Error notification serializer.
 */
public final class ErrorNotificationSerializer extends AbstractSerializer {

    private static ErrorNotificationSerializer eventSerializer;

    private ErrorNotificationSerializer() {
        super();
    }


    /**
     * Instance error notification serializer.
     *
     * @return the error notification serializer
     */
    public static synchronized ErrorNotificationSerializer instance() {
        if (eventSerializer == null) {
            eventSerializer = new ErrorNotificationSerializer();
        }
        return eventSerializer;
    }


    /**
     * Deserialize error notification.
     *
     * @param aSerialization the a serialization
     * @return the error notification
     */
    public ErrorNotification deserialize(String aSerialization) {
        return gson.fromJson(aSerialization, ErrorNotification.class);
    }


    /**
     * Serialize string.
     *
     * @param object the object
     * @return the string
     */
    public String serialize(ErrorNotification object) {
        return gson.toJson(object);
    }

}