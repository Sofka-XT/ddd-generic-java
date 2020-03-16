package co.com.sofka.test;

import co.com.sofka.business.asyn.ListenerEvent;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.AuditPasswordUseCase;
import co.com.sofka.business.UpdatePasswordUseCase;
import co.com.sofka.domain.user.events.UserCreated;
import co.com.sofka.domain.user.events.UserPasswordUpdated;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.user.values.UserId;
import co.com.sofka.domain.user.values.UserName;
import co.com.sofka.domain.user.values.UserPassword;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.repository.QueryFaultException;
import co.com.sofka.infraestructure.store.StoredEvent;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UpdatePasswordUseCaseTest {
    private static final Logger logger = LoggerFactory.getLogger(UpdatePasswordUseCaseTest.class);
    UpdatePasswordUseCase useCase;

    public UpdatePasswordUseCaseTest() {
        this.useCase = new UpdatePasswordUseCase(new EventStoreRepository<UserId>() {
            @Override
            public List<DomainEvent> getEventsBy(UserId aggregateRootId) throws QueryFaultException {
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

            }
        });
    }

    @Test
    public void updatePassword() throws InterruptedException {
        var request = new UpdatePasswordUseCase.Request(
                new UserId("uuuu-iiiii-dddddd"),
                new UserPassword("ddddddd")
        );


        Set<UseCase<? extends UseCase.RequestEvent,? extends ResponseEvents>> useCases = new HashSet<>();
        useCases.add(new AuditPasswordUseCase());

        UseCaseHandler.getInstance()
              .asyncExecutor(useCase, request)
              .subscribe(new ListenerEvent(useCases){
                  @Override
                  public void onComplete() {
                      super.onComplete();
                  }
              });

        try {
            Thread.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
