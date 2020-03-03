package co.com.sofka.domain;

import co.com.sofka.domain.events.UserCreated;
import co.com.sofka.domain.events.UserPasswordUpdated;
import co.com.sofka.domain.generic.AggregateRoot;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.values.UserId;
import co.com.sofka.domain.values.UserName;
import co.com.sofka.domain.values.UserPassword;

import java.util.*;
public  class User extends AggregateRoot<UserId> {

    private UserName userName;
    private UserPassword userPassword;

    private Behaviors behaviors;


    public User(UserId userId, UserName aUserName, UserPassword aUserPassword) {
        this(userId);//initialize object base
        var userPassword = Objects.requireNonNull(aUserPassword);
        var userName = Objects.requireNonNull(aUserName);
        if(userPassword.value().length() < 4){
            throw new IllegalArgumentException("The password must be greater than 4 characters");
        }
        if(userName.value().length() < 5){
            throw new IllegalArgumentException("The username must be greater than 5 characters");
        }
        appendChange(new UserCreated(userName, userPassword)).apply();
    }


    private User(UserId userId){
        super(userId);
        registerEntityBehavior(new Behaviors(this));
    }

    public void updateUserPassword(UserPassword aUserPassword) {
        var userPassword = Objects.requireNonNull(aUserPassword);
        if(this.userPassword.equals(userPassword)){
            throw new IllegalArgumentException("The password are equal");
        }
        appendChange(new UserPasswordUpdated(userPassword)).apply();
    }

    /**
     * User aggregate behaviors for entity valid
     */
    public static class Behaviors extends EntityBehaviors<User> {
        private Behaviors(User entity){
            super(entity);
        }
        {
            add((UserPasswordUpdated event) -> {//change status
                entity.userPassword = event.getUserPassword();
            });

            add((UserCreated domainEvent) -> { //change status
                entity.userName = domainEvent.getUserName();
                entity.userPassword = domainEvent.getUserPassword();
            });

        }

    }

    public static User from(UserId userId, List<DomainEvent> eventList){
        User user = new User(userId);
        eventList.forEach(user::applyEvent);
        return user;
    }
}
