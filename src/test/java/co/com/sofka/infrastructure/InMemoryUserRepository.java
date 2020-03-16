package co.com.sofka.infrastructure;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.user.values.UserId;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.repository.QueryFaultException;
import co.com.sofka.infraestructure.store.StoredEvent;

import java.util.List;

public class InMemoryUserRepository implements EventStoreRepository<UserId> {
    @Override
    public List<DomainEvent> getEventsBy(UserId aggregateRootId) throws QueryFaultException {
        return null;
    }

    @Override
    public void saveEvent(UserId aggregateRootId, StoredEvent storedEvent) {

    }
}
