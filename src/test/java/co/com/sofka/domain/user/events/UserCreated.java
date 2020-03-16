package co.com.sofka.domain.user.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.user.values.UserId;
import co.com.sofka.domain.user.values.UserName;
import co.com.sofka.domain.user.values.UserPassword;

public class UserCreated extends DomainEvent {
    final UserName userName;
    final UserPassword userPassword;


    public UserCreated(UserId userId, UserName userName, UserPassword userPassword) {
        super("user.created", userId);
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public UserName getUserName() {
        return userName;
    }

    public UserPassword getUserPassword() {
        return userPassword;
    }

}
