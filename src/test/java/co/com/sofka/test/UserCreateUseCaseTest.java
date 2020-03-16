package co.com.sofka.test;

import co.com.sofka.business.asyn.SubscriberEvent;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.UserCreateUseCase;
import co.com.sofka.domain.user.events.UserCreated;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.user.values.UserId;
import co.com.sofka.domain.user.values.UserName;
import co.com.sofka.domain.user.values.UserPassword;
import co.com.sofka.infraestructure.bus.ErrorEvent;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.store.StoredEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Flow;

public class UserCreateUseCaseTest {

    private UserCreateUseCase useCase;

    public UserCreateUseCaseTest() {
        this.useCase = new UserCreateUseCase();
    }

    @Test
    public void createUserWithEmitSuccess() {
        var userName = new UserName("rauloko");
        var userPassword = new UserPassword("asdasd");

        UserCreateUseCase.Request request = new UserCreateUseCase.Request(userName, userPassword);

        UseCaseHandler.getInstance()
                .asyncExecutor(useCase, request)
                .subscribe(new Flow.Subscriber<>() {
                    @Override
                    public void onSubscribe(Flow.Subscription subscription) {

                    }

                    @Override
                    public void onNext(DomainEvent event) {
                        Assertions.assertEquals("user.created", event.type);
                        var userCreatedEvent = (UserCreated) event;

                        Assertions.assertEquals("asdasd", userCreatedEvent.getUserPassword().value());
                        Assertions.assertEquals("rauloko", userCreatedEvent.getUserName().value());
                        Assertions.assertEquals(1, userCreatedEvent.versionType());
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Assertions.fail("A problem inside usecase");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Test
    public void createUserWithEmitSuccess_SaveRepository() {
        var userName = new UserName("rauloko");
        var userPassword = new UserPassword("asdasd");

        UserCreateUseCase.Request request = new UserCreateUseCase.Request(userName, userPassword);

        EventStoreRepository<UserId> repository = new EventStoreRepository<>() {
            @Override
            public List<DomainEvent> getEventsBy(UserId aggregateRootId) {
                return null;
            }

            @Override
            public void saveEvent(UserId aggregateRootId, StoredEvent storedEvent) {
                Assertions.assertTrue(aggregateRootId.value().length() > 10);
                Assertions.assertEquals("co.com.sofka.domain.user.events.UserCreated", storedEvent.getTypeName());
            }
        };

        EventBus publisher = new EventBus() {
            @Override
            public void publish(DomainEvent event) {
                Assertions.assertEquals("user.created", event.type);
                var userCreatedEvent = (UserCreated) event;

                Assertions.assertEquals("asdasd", userCreatedEvent.getUserPassword().value());
                Assertions.assertEquals("rauloko", userCreatedEvent.getUserName().value());
                Assertions.assertEquals(1, userCreatedEvent.versionType());
            }

            @Override
            public void publishError(ErrorEvent errorEvent) {

            }
        };


        UseCaseHandler.getInstance()
                .asyncExecutor(useCase, request)
                .subscribe(new SubscriberEvent<>(repository, publisher));
    }
}
