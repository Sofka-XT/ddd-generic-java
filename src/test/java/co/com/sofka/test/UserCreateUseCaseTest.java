package co.com.sofka.test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import co.com.sofka.LoggerTestUtil;
import co.com.sofka.business.UserCreateUseCase;
import co.com.sofka.business.asyn.SubscriberEvent;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.domain.user.values.UserId;
import co.com.sofka.domain.user.values.UserName;
import co.com.sofka.domain.user.values.UserPassword;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infrastructure.InMemoryEvenBus;
import co.com.sofka.infrastructure.InMemoryUserRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserCreateUseCaseTest {

    private UserCreateUseCase useCase;
    private EventStoreRepository<UserId> repository;
    private EventBus eventBus;
    private ListAppender<ILoggingEvent> loggingBus;
    private ListAppender<ILoggingEvent> loggingRepo;
    private ListAppender<ILoggingEvent> loggingUsecase;
    private ListAppender<ILoggingEvent> loggingSubscriberEvent;

    @BeforeEach
    public void setup() {
        this.loggingBus = LoggerTestUtil.getListAppenderForClass(InMemoryEvenBus.class);
        this.loggingRepo = LoggerTestUtil.getListAppenderForClass(InMemoryUserRepository.class);
        this.loggingUsecase = LoggerTestUtil.getListAppenderForClass(UserCreateUseCase.class);
        this.loggingSubscriberEvent = LoggerTestUtil.getListAppenderForClass(SubscriberEvent.class);
        this.repository = new InMemoryUserRepository();
        this.eventBus = new InMemoryEvenBus();
        this.useCase = new UserCreateUseCase();
    }


    @Test
    public void createUserWithEmitSuccess() throws InterruptedException {
        var userName = new UserName("rauloko");
        var userPassword = new UserPassword("asdasd");

        UserCreateUseCase.Request request = new UserCreateUseCase.Request(userName, userPassword);

        UseCaseHandler.getInstance()
                .asyncExecutor(useCase, request)
                .subscribe(new SubscriberEvent<>());

        Thread.sleep(100);

        assertThat(loggingSubscriberEvent.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(Tuple.tuple("onNext[user.created]", Level.DEBUG))
                .contains(Tuple.tuple("onComplete[SubscriberEvent]", Level.DEBUG));
    }

    @Test
    public void createUserWithEmitSuccess_SaveRepository() throws InterruptedException {
        var userName = new UserName("rauloko");
        var userPassword = new UserPassword("asdasd");

        UserCreateUseCase.Request request = new UserCreateUseCase.Request(userName, userPassword);

        UseCaseHandler.getInstance()
                .asyncExecutor(useCase, request)
                .subscribe(new SubscriberEvent<>(repository, eventBus));

        Thread.sleep(100);

        assertThat(loggingBus.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(Tuple.tuple("publish -> user.created", Level.INFO));

        assertThat(loggingRepo.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .anyMatch(tuple -> ((String) tuple.toList().get(0)).contains("UserCreated"));

        assertThat(loggingUsecase.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(Tuple.tuple("executeUseCase[UserCreateUseCase]", Level.INFO));
    }
}
