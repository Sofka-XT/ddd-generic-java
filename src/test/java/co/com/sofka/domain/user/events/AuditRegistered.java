package co.com.sofka.domain.user.events;

import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;

public class AuditRegistered extends DomainEvent {
    /**
     * Instantiates a new Domain event.
     *
     * @param type            the type
     * @param aggregateRootId the aggregate root id
     */
    public AuditRegistered(AggregateRootId aggregateRootId) {
        super("audit.register", aggregateRootId);
    }
}
