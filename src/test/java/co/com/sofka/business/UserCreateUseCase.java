package co.com.sofka.business;

import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.User;
import co.com.sofka.domain.values.UserId;
import co.com.sofka.domain.values.UserName;
import co.com.sofka.domain.values.UserPassword;


public class UserCreateUseCase extends UseCase<UserCreateUseCase.Request, ResponseEvents> {


    @Override
    protected void executeUseCase(Request request) {
        UserId userId = UserId.create();
        User user = new User(userId, request.userName, request.userPassword);
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
