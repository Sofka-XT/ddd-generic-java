package co.com.sofka.business;


import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.user.UserAggregate;
import co.com.sofka.domain.user.values.UserId;
import co.com.sofka.domain.user.values.UserPassword;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import co.com.sofka.infraestructure.repository.QueryFaultException;

public class UpdatePasswordUseCase extends UseCase<UpdatePasswordUseCase.Request, ResponseEvents> {

    EventStoreRepository<UserId> repository;

    public UpdatePasswordUseCase(EventStoreRepository<UserId> repository) {
        this.repository = repository;
    }

    @Override
    protected void executeUseCase(Request request) {
        var userId = request.userId;
        try {
            var user = UserAggregate.from(userId, repository.getEventsBy(userId));
            user.updateUserPassword(request.newPassword);
            emit().onSuccess(new ResponseEvents(user.getUncommittedChanges()));
            System.out.println("the password is updated");
        } catch (QueryFaultException e) {
            emit().onError(new RuntimeException(e.getCause()));
        }
    }

    public static class Request implements UseCase.RequestValues {
        private final UserId userId;
        private final UserPassword newPassword;

        public Request(UserId userId, UserPassword newPassword) {
            this.userId = userId;
            this.newPassword = newPassword;
        }
    }

}
