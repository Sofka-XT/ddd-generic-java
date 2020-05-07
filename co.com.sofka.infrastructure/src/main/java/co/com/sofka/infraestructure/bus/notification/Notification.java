package co.com.sofka.infraestructure.bus.notification;


import java.util.Date;

public class Notification {
    private String origin;
    private String body;
    private Date occurredOn;
    private String typeName;

    public Notification() {
    }

    public Notification(String origin, String typeName, Date occurredOn, String body) {
        this.origin = origin;
        this.body = body;
        this.occurredOn = occurredOn;
        this.typeName = typeName;
    }

    public String getOrigin() {
        return origin;
    }

    public String getBody() {
        return body;
    }

    public Date getOccurredOn() {
        return occurredOn;
    }

    public String getTypeName() {
        return typeName;
    }


}
