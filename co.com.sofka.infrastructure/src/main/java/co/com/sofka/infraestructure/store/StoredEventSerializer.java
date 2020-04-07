package co.com.sofka.infraestructure.store;

import co.com.sofka.infraestructure.AbstractSerializer;


/**
 * The type Stored event serializer.
 */
public final class StoredEventSerializer extends AbstractSerializer {

    private static StoredEventSerializer eventSerializer;

    private StoredEventSerializer() {
        super();
    }

    /**
     * Instance stored event serializer.
     *
     * @return the stored event serializer
     */
    public static synchronized StoredEventSerializer instance() {
        if (StoredEventSerializer.eventSerializer == null) {
            StoredEventSerializer.eventSerializer = new StoredEventSerializer();
        }
        return StoredEventSerializer.eventSerializer;
    }

    /**
     * Deserialize stored event.
     *
     * @param aSerialization the a serialization
     * @param aType          the a type
     * @return the stored event
     */
    public StoredEvent deserialize(String aSerialization, Class<StoredEvent> aType) {
        return gson.fromJson(aSerialization, aType);
    }

    /**
     * Serialize string.
     *
     * @param object the object
     * @return the string
     */
    public String serialize(StoredEvent object) {
        return gson.toJson(object);
    }

}
