package co.com.sofka.domain.events;

import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.values.UserPassword;

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
