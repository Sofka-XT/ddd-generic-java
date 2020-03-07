package co.com.sofka.domain;

import co.com.sofka.domain.events.UserCreated;
import co.com.sofka.domain.events.UserPasswordUpdated;
import co.com.sofka.domain.generic.AggregateEvent;

/**
 * User aggregate behaviors for entity valid
 */
public final class UserBehaviors extends AggregateEvent.EventBehaviors<User> {

    {
        add((UserPasswordUpdated event) -> {//change status
            if (entity.userPassword.equals(event.userPassword)) {
                throw new IllegalArgumentException("The password are equal");
            }
            entity.userPassword = event.getUserPassword();
        });

        add((UserCreated domainEvent) -> { //change status
            if (entity.userPassword.value().length() < 4) {
                throw new IllegalArgumentException("The password must be greater than 4 characters");
            }
            if (entity.userName.value().length() < 5) {
                throw new IllegalArgumentException("The username must be greater than 5 characters");
            }
            entity.userName = domainEvent.getUserName();
            entity.userPassword = domainEvent.getUserPassword();
        });

    }

    protected UserBehaviors(User entity) {
        super(entity);
    }

}
