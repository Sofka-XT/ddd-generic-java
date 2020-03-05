package co.com.sofka.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.values.UserId;
import co.com.sofka.domain.values.UserPassword;

public class UserPasswordUpdated extends DomainEvent {
    public final UserPassword userPassword;

    public UserPasswordUpdated(UserId userId, UserPassword userPassword) {
        super("user.password.updated", userId);
        this.userPassword = userPassword;
    }

    public UserPassword getUserPassword() {
        return userPassword;
    }
}
