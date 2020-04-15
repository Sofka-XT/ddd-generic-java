package co.com.sofka.infraestructure.store;

import co.com.sofka.infraestructure.AbstractSerializer;
import co.com.sofka.infraestructure.bus.ErrorEvent;

import java.lang.reflect.Type;


/**
 * The type Event serializer.
 */
public final class EventErrorSerializer extends AbstractSerializer {

    private static EventErrorSerializer eventSerializer;

    private EventErrorSerializer() {
        super();
    }

    /**
     * Instance event serializer.
     *
     * @return the event serializer
     */
    public static synchronized EventErrorSerializer instance() {
        if (EventErrorSerializer.eventSerializer == null) {
            EventErrorSerializer.eventSerializer = new EventErrorSerializer();
        }
        return EventErrorSerializer.eventSerializer;
    }


    /**
     * Deserialize t.
     *
     * @param <T>            the type parameter
     * @param aSerialization the a serialization
     * @param aType          the a type
     * @return the t
     */
    public <T extends ErrorEvent> T deserialize(String aSerialization, final Class<?> aType) {
        return gson.fromJson(aSerialization, (Type) aType);
    }

    /**
     * Serialize string.
     *
     * @param object the object
     * @return the string
     */
    public String serialize(ErrorEvent object) {
        return gson.toJson(object);
    }

}
