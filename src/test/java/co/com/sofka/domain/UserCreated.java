package co.com.sofka.domain;

import co.com.sofka.domain.generic.DomainEvent;

public  class UserCreated extends DomainEvent {
    final UserName userName;
    final UserPassword userPassword;

    public UserCreated(UserName userName, UserPassword userPassword) {
        super("user_created");
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
