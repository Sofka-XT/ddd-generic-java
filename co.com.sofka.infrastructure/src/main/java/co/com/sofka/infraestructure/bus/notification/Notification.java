package co.com.sofka.infraestructure.bus.notification;


import java.util.Date;

/**
 * The type Notification.
 */
public class Notification {
    private String origin;
    private String body;
    private Date occurredOn;
    private String typeName;

    /**
     * Instantiates a new Notification.
     */
    public Notification() {
    }

    /**
     * Instantiates a new Notification.
     *
     * @param origin     the origin
     * @param typeName   the type name
     * @param occurredOn the occurred on
     * @param body       the body
     */
    public Notification(String origin, String typeName, Date occurredOn, String body) {
        this.origin = origin;
        this.body = body;
        this.occurredOn = occurredOn;
        this.typeName = typeName;
    }

    /**
     * Gets origin.
     *
     * @return the origin
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Gets body.
     *
     * @return the body
     */
    public String getBody() {
        return body;
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
     * Gets type name.
     *
     * @return the type name
     */
    public String getTypeName() {
        return typeName;
    }


}
