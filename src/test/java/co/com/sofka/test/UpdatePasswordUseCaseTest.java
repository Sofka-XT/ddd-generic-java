package co.com.sofka.test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import co.com.sofka.LoggerTestUtil;
import co.com.sofka.business.AuditPasswordUseCase;
import co.com.sofka.business.UpdatePasswordUseCase;
import co.com.sofka.business.asyn.ListenerEvent;
import co.com.sofka.business.asyn.SubscriberEvent;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.user.values.UserId;
import co.com.sofka.domain.user.values.UserPassword;
import co.com.sofka.infraestructure.bus.EventBus;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infrastructure.InMemoryEvenBus;
import co.com.sofka.infrastructure.InMemoryUserRepository;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdatePasswordUseCaseTest {

    private UpdatePasswordUseCase useCase;
    private EventStoreRepository<UserId> repository;
    private EventBus eventBus;
    private ListAppender<ILoggingEvent> loggingBus;
    private ListAppender<ILoggingEvent> loggingRepo;
    private ListAppender<ILoggingEvent> loggingAudit;

    @BeforeEach
    public void setup() {
        this.loggingBus = LoggerTestUtil.getListAppenderForClass(InMemoryEvenBus.class);
        this.loggingRepo = LoggerTestUtil.getListAppenderForClass(InMemoryUserRepository.class);
        this.loggingAudit = LoggerTestUtil.getListAppenderForClass(AuditPasswordUseCase.class);
        this.repository = new InMemoryUserRepository();
        this.eventBus = new InMemoryEvenBus();
        this.useCase = new UpdatePasswordUseCase(repository);
    }


    @Test
    public void updatePassword() throws InterruptedException {
        var request = new UpdatePasswordUseCase.Request(
                new UserId("uuuu-iiiii-dddddd"),
                new UserPassword("ddddddd")
        );

        Set<UseCase<? extends UseCase.RequestEvent, ? extends ResponseEvents>> useCases = new HashSet<>();
        useCases.add(new AuditPasswordUseCase());

        UseCaseHandler.getInstance()
                .asyncExecutor(useCase, request)
                .subscribe(new SubscriberEvent<>(repository, eventBus, new ListenerEvent(useCases) {
                    @Override
                    public void onComplete() {
                    }
                }));
        LoggerFactory.getLogger(InMemoryEvenBus.class).getName();
        Thread.sleep(100);

        assertThat(loggingBus.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(Tuple.tuple("publish -> audit.register", Level.INFO));

        assertThat(loggingRepo.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(Tuple.tuple("save -> uuuu-iiiii-dddddd,co.com.sofka.domain.user.events.UserPasswordUpdated", Level.INFO));

        assertThat(loggingAudit.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(Tuple.tuple("executeUseCase[AuditPasswordUseCase]uuuu-iiiii-dddddd", Level.INFO));

    }
}
