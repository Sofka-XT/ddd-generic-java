package co.com.sofka.test;

import co.com.sofka.infrastructure.handles.UserCommandHandles;
import co.com.sofka.infrastructure.handles.UserQueryHandles;
import co.com.sofka.domain.user.commands.CreateUserCommand;
import co.com.sofka.domain.user.commands.UpdateUsernameCommand;
import co.com.sofka.domain.user.queries.All;
import co.com.sofka.infrastructure.view.ListUserView;
import co.com.sofka.infraestructure.handle.ExecutionNoFound;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserHandleTest {
    @Test
    public void executorCommand(){
        UserCommandHandles handles = new UserCommandHandles();
        handles.execute(new CreateUserCommand());
    }

    @Test
    public void executorQuery(){
        UserQueryHandles handles = new UserQueryHandles(null);
        ListUserView result = (ListUserView) handles.search(new All());

    }


    @Test
    public void executorCommandError(){
        Assertions.assertThrows(ExecutionNoFound.class, () -> {
            UserCommandHandles handles = new UserCommandHandles();
            handles.execute(new UpdateUsernameCommand());
        });

    }
}
