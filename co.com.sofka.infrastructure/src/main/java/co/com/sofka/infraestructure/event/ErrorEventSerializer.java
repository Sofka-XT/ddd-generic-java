package co.com.sofka.infraestructure.event;

import co.com.sofka.infraestructure.AbstractSerializer;

import java.lang.reflect.Type;


/**
 * The type Error event serializer.
 */
public final class ErrorEventSerializer extends AbstractSerializer {

    private static ErrorEventSerializer eventSerializer;

    private ErrorEventSerializer() {
        super();
    }

    /**
     * Instance error event serializer.
     *
     * @return the error event serializer
     */
    public static synchronized ErrorEventSerializer instance() {
        if (ErrorEventSerializer.eventSerializer == null) {
            ErrorEventSerializer.eventSerializer = new ErrorEventSerializer();
        }
        return ErrorEventSerializer.eventSerializer;
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
