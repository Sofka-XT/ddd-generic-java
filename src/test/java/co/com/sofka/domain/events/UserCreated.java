package co.com.sofka.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.values.UserId;
import co.com.sofka.domain.values.UserName;
import co.com.sofka.domain.values.UserPassword;

public  class UserCreated extends DomainEvent {
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
