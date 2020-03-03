package co.com.sofka.infraestructure.store;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.AbstractSerializer;

import java.lang.reflect.Type;


public final class EventSerializer extends AbstractSerializer {

    private EventSerializer(){
        super();
    }

    private static EventSerializer eventSerializer;

    public static synchronized EventSerializer instance() {
        if (EventSerializer.eventSerializer == null) {
            EventSerializer.eventSerializer = new EventSerializer();
        }
        return EventSerializer.eventSerializer;
    }


    public <T extends DomainEvent> T deserialize(String aSerialization, final Class<?> aType) {
        return gson.fromJson(aSerialization, (Type) aType);
    }

    public String serialize(DomainEvent object) {
        return gson.toJson(object);
    }

}
