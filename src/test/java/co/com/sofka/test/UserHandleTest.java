package co.com.sofka.test;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.read.ListAppender;
import co.com.sofka.LoggerTestUtil;
import co.com.sofka.business.UserCreateUseCase;
import co.com.sofka.business.asyn.SubscriberEvent;
import co.com.sofka.domain.user.commands.CreateUserCommand;
import co.com.sofka.domain.user.commands.UpdateUsernameCommand;
import co.com.sofka.domain.user.queries.All;
import co.com.sofka.domain.user.queries.FindById;
import co.com.sofka.infraestructure.handle.CommandExecutor;
import co.com.sofka.infraestructure.handle.ExecutionNoFound;
import co.com.sofka.infrastructure.InMemoryQueryRepository;
import co.com.sofka.infrastructure.handles.UserCommandHandles;
import co.com.sofka.infrastructure.handles.UserQueryHandles;
import co.com.sofka.infrastructure.view.ListUserView;
import co.com.sofka.infrastructure.view.UserView;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserHandleTest {
    private ListAppender<ILoggingEvent> loggingCommand;
    private ListAppender<ILoggingEvent> loggingUsecase;

    @BeforeEach
    public void setup() {
        this.loggingCommand = LoggerTestUtil.getListAppenderForClass(CommandExecutor.class);
        this.loggingUsecase = LoggerTestUtil.getListAppenderForClass(UserCreateUseCase.class);
    }

    @Test
    public void executorCommand() throws InterruptedException {
        UserCommandHandles handles = new UserCommandHandles(new SubscriberEvent<>());

        var command = new CreateUserCommand();
        command.setName("rauloko");
        command.setPassword("asdasdasd");
        handles.execute(command);

        Thread.sleep(100);

        assertThat(loggingCommand.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(Tuple.tuple("executor[user.create]", Level.DEBUG));
        assertThat(loggingUsecase.list)
                .extracting(ILoggingEvent::getMessage, ILoggingEvent::getLevel)
                .contains(Tuple.tuple("executeUseCase[UserCreateUseCase]", Level.INFO));
    }

    @Test
    public void executorQuery() {
        var repo = new InMemoryQueryRepository();
        UserQueryHandles handles = new UserQueryHandles(repo);
        var resultList = (ListUserView) handles.search(new All());
        Assertions.assertEquals(4, resultList.getUserList().size());

        var resultGet = (UserView) handles.search(new FindById(1L));
        Assertions.assertEquals("raul.alzate", resultGet.getUser().getName());


    }


    @Test
    public void executorCommandError() {
        Assertions.assertThrows(ExecutionNoFound.class, () -> {
            UserCommandHandles handles = new UserCommandHandles(new SubscriberEvent<>());
            handles.execute(new UpdateUsernameCommand());
        });

    }
}
