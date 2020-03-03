package co.com.sofka.infraestructure.store;

import co.com.sofka.infraestructure.AbstractSerializer;


public final class StoredEventSerializer extends AbstractSerializer {

    private StoredEventSerializer(){
        super();
    }

    private static StoredEventSerializer eventSerializer;

    public static synchronized StoredEventSerializer instance() {
        if (StoredEventSerializer.eventSerializer == null) {
            StoredEventSerializer.eventSerializer = new StoredEventSerializer();
        }
        return StoredEventSerializer.eventSerializer;
    }

    public StoredEvent deserialize(String aSerialization, Class<StoredEvent> aType) {
        return gson.fromJson(aSerialization, aType);
    }

    public String serialize(StoredEvent object) {
        return gson.toJson(object);
    }

}
