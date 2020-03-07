package co.com.sofka.domain;

import co.com.sofka.domain.events.UserCreated;
import co.com.sofka.domain.events.UserPasswordUpdated;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.values.UserId;
import co.com.sofka.domain.values.UserName;
import co.com.sofka.domain.values.UserPassword;

import java.util.List;
import java.util.Objects;

public class User extends AggregateEvent<UserId> {

    protected UserName userName;
    protected UserPassword userPassword;


    public User(UserId userId, UserName aUserName, UserPassword aUserPassword) {
        this(userId);//initialize object base
        var userPassword = Objects.requireNonNull(aUserPassword);
        var userName = Objects.requireNonNull(aUserName);
        appendChange(new UserCreated(userId, userName, userPassword)).apply();
    }


    private User(UserId userId) {
        super(userId);
        registerEntityBehavior(new UserBehaviors(this));
    }


    public static User from(UserId userId, List<DomainEvent> eventList) {
        User user = new User(userId);
        eventList.forEach(user::applyEvent);
        return user;
    }

    public void updateUserPassword(UserPassword aUserPassword) {
        var userPassword = Objects.requireNonNull(aUserPassword);
        appendChange(new UserPasswordUpdated(this.entityId, userPassword)).apply();
    }

}
