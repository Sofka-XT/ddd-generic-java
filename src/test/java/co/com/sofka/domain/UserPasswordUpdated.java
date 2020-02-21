package co.com.sofka.domain;

import co.com.sofka.domain.generic.DomainEvent;

public  class UserPasswordUpdated extends DomainEvent {
    public final UserPassword userPassword;

    protected UserPasswordUpdated(UserPassword userPassword) {
        super("user_password_updated");
        this.userPassword = userPassword;
    }

    public UserPassword getUserPassword(){
        return userPassword;
    }
}
