package co.com.sofka.infraestructure.store;

import co.com.sofka.domain.generic.DomainEvent;
import java.util.Date;

public final class StoredEvent {

    private String eventBody;
    private Date occurredOn;
    private String typeName;

    public StoredEvent() {
    }

    public StoredEvent(String typeName, Date occurredOn, String eventBody) {
        this.setEventBody(eventBody);
        this.setOccurredOn(occurredOn);
        this.setTypeName(typeName);
    }

    public void setEventBody(String eventBody) {
        this.eventBody = eventBody;
    }

    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getEventBody() {
        return eventBody;
    }

    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getTypeName() {
        return typeName;
    }

    public static StoredEvent wrapEvent(DomainEvent domainEvent) {
        return new StoredEvent(domainEvent.getClass().getCanonicalName(),
                new Date(domainEvent.when.toEpochMilli()),
                serializeEvent(domainEvent)
        );
    }

    private static String serializeEvent(DomainEvent domainEvent) {
        return EventSerializer.instance().serialize(domainEvent);
    }

    @Override
    public String toString() {
        return StoredEventSerializer.instance().serialize(this);
    }
}
