package co.com.sofka.business;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.events.UserCreated;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.values.UserName;
import co.com.sofka.domain.values.UserPassword;

import java.util.List;

public class UserCreateUseCase extends UseCase<UserCreateUseCase.Request, UserCreateUseCase.Response> {


    @Override
    protected void executeUseCase(Request objectInput) {
        var event = new UserCreated(new UserName(""), new UserPassword(""));
        emit().onSuccess(new Response(List.of(event)));
    }

    public static class Request implements UseCase.RequestValues{
    }

    public static class Response implements UseCase.PubEvents{
        private final List<DomainEvent> events;

        public Response(List<DomainEvent> events) {
            this.events = events;
        }

        @Override
        public List<DomainEvent> getDomainEvents() {
            return events;
        }
    }
}
