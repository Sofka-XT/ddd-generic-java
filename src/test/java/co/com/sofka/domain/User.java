package co.com.sofka.domain;

import co.com.sofka.domain.events.UserCreated;
import co.com.sofka.domain.events.UserPasswordUpdated;
import co.com.sofka.domain.generic.AggregateRoot;
import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.values.UserName;
import co.com.sofka.domain.values.UserPassword;

import java.util.List;
import java.util.function.Consumer;

public  class User extends AggregateRoot {

    private UserName userName;
    private UserPassword userPassword;

    private Consumer<UserCreated> createUser = domainEvent -> {
        this.userName = domainEvent.getUserName();
        this.userPassword = domainEvent.getUserPassword();
    };

    private Consumer<UserPasswordUpdated> updatePassword = event -> {
        this.userPassword = event.getUserPassword();
    };

    public User(AggregateRootId aggregateRootId, UserName userName, UserPassword userPassword) {
        this(aggregateRootId);
        appendChange(new UserCreated(userName, userPassword)).apply(createUser);
    }


    private User(AggregateRootId aggregateRootId){
        super(aggregateRootId);
        registerActions(createUser, updatePassword);
    }


    public static User from(AggregateRootId aggregateRootId, List<DomainEvent> eventList){
        User user = new User(aggregateRootId);
        eventList.forEach(user::applyEvent);
        return user;
    }

    public void updateUserPassword(UserPassword userPassword) {
        appendChange(new UserPasswordUpdated(userPassword)).apply(updatePassword);
    }
}
