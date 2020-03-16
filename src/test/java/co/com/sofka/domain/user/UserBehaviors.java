package co.com.sofka.domain.user;

import co.com.sofka.domain.generic.EventBehaviors;
import co.com.sofka.domain.user.events.UserCreated;
import co.com.sofka.domain.user.events.UserPasswordUpdated;

/**
 * User aggregate behaviors for entity valid
 */
public final class UserBehaviors extends EventBehaviors<UserAggregate> {

    {
        add((UserPasswordUpdated event) -> {//change status
            if (entity.userPassword.equals(event.userPassword)) {
                throw new IllegalArgumentException("The password are equal");
            }
            entity.userPassword = event.getUserPassword();
        });

        add((UserCreated domainEvent) -> { //change status
            if (domainEvent.getUserPassword().value().length() < 4) {
                throw new IllegalArgumentException("The password must be greater than 4 characters");
            }
            if (domainEvent.getUserName().value().length() < 5) {
                throw new IllegalArgumentException("The username must be greater than 5 characters");
            }
            entity.userName = domainEvent.getUserName();
            entity.userPassword = domainEvent.getUserPassword();
        });

    }

    protected UserBehaviors(UserAggregate entity) {
        super(entity);
    }

}
