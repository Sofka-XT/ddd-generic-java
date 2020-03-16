package co.com.sofka.infrastructure.handles;

import co.com.sofka.business.UserCreateUseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.domain.user.commands.CreateUserCommand;
import co.com.sofka.domain.user.values.UserName;
import co.com.sofka.domain.user.values.UserPassword;
import co.com.sofka.infraestructure.annotation.CommandHandles;
import co.com.sofka.infraestructure.handle.CommandExecutor;

@CommandHandles
public final class UserCommandHandles extends CommandExecutor {
    {
        add((CreateUserCommand command) -> {
            UserName userName = new UserName(command.getName());
            UserPassword userPassword = new UserPassword(command.getPassword());
            UseCaseHandler.getInstance()
                    .asyncExecutor(new UserCreateUseCase(),
                            new UserCreateUseCase.Request(userName, userPassword));
        });

    }
}

