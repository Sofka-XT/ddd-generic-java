package co.com.sofka.infraestructure.store;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.AbstractSerializer;

import java.lang.reflect.Type;


/**
 * The type Event serializer.
 */
public final class EventSerializer extends AbstractSerializer {

    private static EventSerializer eventSerializer;

    private EventSerializer() {
        super();
    }

    /**
     * Instance event serializer.
     *
     * @return the event serializer
     */
    public static synchronized EventSerializer instance() {
        if (EventSerializer.eventSerializer == null) {
            EventSerializer.eventSerializer = new EventSerializer();
        }
        return EventSerializer.eventSerializer;
    }


    /**
     * Deserialize t.
     *
     * @param <T>            the type parameter
     * @param aSerialization the a serialization
     * @param aType          the a type
     * @return the t
     */
    public <T extends DomainEvent> T deserialize(String aSerialization, final Class<?> aType) {
        return gson.fromJson(aSerialization, (Type) aType);
    }

    /**
     * Serialize string.
     *
     * @param object the object
     * @return the string
     */
    public String serialize(DomainEvent object) {
        return gson.toJson(object);
    }

}
