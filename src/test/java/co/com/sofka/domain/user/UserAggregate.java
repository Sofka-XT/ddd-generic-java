package co.com.sofka.domain.user;

import co.com.sofka.domain.user.events.UserCreated;
import co.com.sofka.domain.user.events.UserPasswordUpdated;
import co.com.sofka.domain.generic.AggregateEvent;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.user.values.UserId;
import co.com.sofka.domain.user.values.UserName;
import co.com.sofka.domain.user.values.UserPassword;
import co.com.sofka.infraestructure.annotation.Aggregate;

import java.util.List;
import java.util.Objects;

@Aggregate
public class UserAggregate extends AggregateEvent<UserId> {

    protected UserName userName;
    protected UserPassword userPassword;


    public UserAggregate(UserId userId, UserName aUserName, UserPassword aUserPassword) {
        this(userId);//initialize object base
        var userPassword = Objects.requireNonNull(aUserPassword);
        var userName = Objects.requireNonNull(aUserName);
        appendChange(new UserCreated(userId, userName, userPassword)).apply();
    }


    private UserAggregate(UserId userId) {
        super(userId);
        registerEntityBehavior(new UserBehaviors(this));
    }


    public static UserAggregate from(UserId userId, List<DomainEvent> eventList) {
        UserAggregate user = new UserAggregate(userId);
        eventList.forEach(user::applyEvent);
        return user;
    }

    public void updateUserPassword(UserPassword aUserPassword) {
        var userPassword = Objects.requireNonNull(aUserPassword);
        appendChange(new UserPasswordUpdated(this.entityId, userPassword)).apply();
    }

}
