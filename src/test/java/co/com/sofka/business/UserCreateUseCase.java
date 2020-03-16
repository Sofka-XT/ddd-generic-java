package co.com.sofka.business;

import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.user.UserAggregate;
import co.com.sofka.domain.user.values.UserId;
import co.com.sofka.domain.user.values.UserName;
import co.com.sofka.domain.user.values.UserPassword;


public class UserCreateUseCase extends UseCase<UserCreateUseCase.Request, ResponseEvents> {


    @Override
    protected void executeUseCase(Request request) {
        UserId userId = UserId.create();
        UserAggregate user = new UserAggregate(userId, request.userName, request.userPassword);
        emit().onSuccess(new ResponseEvents(user.getUncommittedChanges()));
    }

    public static class Request implements UseCase.RequestValues {
        private final UserName userName;
        private final UserPassword userPassword;

        public Request(UserName userName, UserPassword userPassword) {
            this.userName = userName;
            this.userPassword = userPassword;
        }
    }
}
