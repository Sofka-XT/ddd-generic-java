package co.com.sofka.business;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.User;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.values.UserId;
import co.com.sofka.domain.values.UserName;
import co.com.sofka.domain.values.UserPassword;

import java.util.List;


public class UserCreateUseCase extends UseCase<UserCreateUseCase.Request, UserCreateUseCase.Response> {


    @Override
    protected void executeUseCase(Request request) {
        UserId userId = UserId.create();
        User user = new User(userId, request.userName, request.userPassword);
        emit().onSuccess(new Response(user.getUncommittedChanges()));
    }

    public static class Request implements UseCase.RequestValues{
        private final UserName userName;
        private final UserPassword userPassword;

        public Request(UserName userName, UserPassword userPassword) {
            this.userName = userName;
            this.userPassword = userPassword;
        }
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
