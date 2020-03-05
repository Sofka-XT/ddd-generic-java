package co.com.sofka.business;


import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.events.UserPasswordUpdated;
import co.com.sofka.domain.generic.AggregateRootId;

public class AuditPasswordUseCase extends UseCase<TriggeredEvent<UserPasswordUpdated>, ResponseEvents> {

    @Override
    protected void executeUseCase(TriggeredEvent<UserPasswordUpdated> request) {
        AggregateRootId aggregateRootId = request.getDomainEvent().aggregateRootId;
        System.out.println("the password is updated ==="+request.getDomainEvent());
    }

}
