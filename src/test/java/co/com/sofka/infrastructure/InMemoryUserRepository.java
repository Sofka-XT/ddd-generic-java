package co.com.sofka.infrastructure;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.user.events.UserCreated;
import co.com.sofka.domain.user.events.UserPasswordUpdated;
import co.com.sofka.domain.user.values.UserId;
import co.com.sofka.domain.user.values.UserName;
import co.com.sofka.domain.user.values.UserPassword;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.store.StoredEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InMemoryUserRepository implements EventStoreRepository<UserId> {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryUserRepository.class);

    @Override
    public List<DomainEvent> getEventsBy(UserId aggregateRootId) {
        var event1 = new UserCreated(
                new UserId("uuuu-iiiii-dddddd"),
                new UserName("Raul Alzate"),
                new UserPassword("asdasd")
        );

        var event2 = new UserPasswordUpdated(
                new UserId("uuuu-iiiii-dddddd"),
                new UserPassword("ffddasdf")
        );
        event2.setVersionType(1L);

        var event3 = new UserPasswordUpdated(
                new UserId("uuuu-iiiii-dddddd"),
                new UserPassword("asdasdasd asdasd")
        );
        event3.setVersionType(2L);

        return List.of(event1, event2, event3);
    }

    @Override
    public void saveEvent(UserId aggregateRootId, StoredEvent storedEvent) {
        logger.info("save -> " + aggregateRootId.value() + "," + storedEvent.getTypeName());
    }
}
