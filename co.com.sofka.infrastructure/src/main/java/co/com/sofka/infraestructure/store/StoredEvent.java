package co.com.sofka.infraestructure.store;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.DeserializeEventException;

import java.util.Date;

/**
 * The type Stored event.
 */
public final class StoredEvent {

    private String eventBody;
    private Date occurredOn;
    private String typeName;

    /**
     * Instantiates a new Stored event.
     */
    public StoredEvent() {
    }

    /**
     * Instantiates a new Stored event.
     *
     * @param typeName   the type name
     * @param occurredOn the occurred on
     * @param eventBody  the event body
     */
    public StoredEvent(String typeName, Date occurredOn, String eventBody) {
        this.setEventBody(eventBody);
        this.setOccurredOn(occurredOn);
        this.setTypeName(typeName);
    }

    /**
     * Wrap event stored event.
     *
     * @param domainEvent the domain event
     * @return the stored event
     */
    public static StoredEvent wrapEvent(DomainEvent domainEvent) {
        return new StoredEvent(domainEvent.getClass().getCanonicalName(),
                new Date(domainEvent.when.toEpochMilli()),
                EventSerializer.instance().serialize(domainEvent)
        );
    }

    /**
     * Gets event body.
     *
     * @return the event body
     */
    public String getEventBody() {
        return eventBody;
    }

    /**
     * Sets event body.
     *
     * @param eventBody the event body
     */
    public void setEventBody(String eventBody) {
        this.eventBody = eventBody;
    }

    /**
     * Gets occurred on.
     *
     * @return the occurred on
     */
    public Date getOccurredOn() {
        return occurredOn;
    }

    /**
     * Sets occurred on.
     *
     * @param occurredOn the occurred on
     */
    public void setOccurredOn(Date occurredOn) {
        this.occurredOn = occurredOn;
    }

    /**
     * Gets type name.
     *
     * @return the type name
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * Sets type name.
     *
     * @param typeName the type name
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Deserialize event domain event.
     *
     * @return the domain event
     */
    public DomainEvent deserializeEvent() {
        try {
            return EventSerializer
                    .instance()
                    .deserialize(this.getEventBody(), Class.forName(this.getTypeName()));
        } catch (ClassNotFoundException e) {
            throw new DeserializeEventException(e.getCause());
        }
    }

    @Override
    public String toString() {
        return StoredEventSerializer.instance().serialize(this);
    }


}
