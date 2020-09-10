package co.com.sofka.business;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;

public class UseCaseEventTest extends UseCase<UseCase.RequestEvent, ResponseEvents> {
    @Override
    public void executeUseCase(RequestEvent input) {
        var event = input.getDomainEvent();
        emit().onSuccess(new ResponseEvents(List.of(
                new NumDomainEvent(1),
                new NumDomainEvent(2),
                new NumDomainEvent(3),
                new NumDomainEvent(4),
                new NumDomainEvent(5),
                new NumDomainEvent(6),
                new NumDomainEvent(7),
                new NumDomainEvent(8),
                new NumDomainEvent(9),
                new NumDomainEvent(10)
        )));
    }

    public static class MyEventRequest implements UseCase.RequestEvent{
        @Override
        public DomainEvent getDomainEvent() {
            return new MyDomainEvent();
        }
    }

}
