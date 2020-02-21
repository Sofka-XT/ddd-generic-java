package co.com.sofka.business;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.domain.*;
import co.com.sofka.domain.events.UserCreated;
import co.com.sofka.domain.events.UserPasswordUpdated;
import co.com.sofka.domain.generic.AggregateRootId;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.domain.values.UserName;
import co.com.sofka.domain.values.UserPassword;
import co.com.sofka.infraestructure.EventStoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Flow;

public class ChangePasswordUseCaseTest {

    @Test
    public void useCaseForChangePasswordSuccess(){
        String pass = "123123";
        String userId = "uuid";
        ChangePasswordUseCase usecase = new ChangePasswordUseCase(new EventStoreRepository() {
            @Override
            public List<DomainEvent> getEventsBy(AggregateRootId aggregateRootId) {
                return List.of(
                        new UserCreated(new UserName(), new UserPassword(""))
                );
            }

            @Override
            public void saveEventsWithAn(AggregateRootId aggregateRootId, List<DomainEvent> events) {
                Assertions.assertTrue(events.get(0) instanceof UserPasswordUpdated);
            }
        });


        var  request = new ChangePasswordUseCase.Request(pass, userId);

        UseCaseHandler.getInstance()
                .execute(usecase,request)
                .subscribe(new Flow.Subscriber<>() {
                    @Override
                    public void onSubscribe(Flow.Subscription subscription) {

                    }

                    @Override
                    public void onNext(DomainEvent domainEvent) {
                        System.out.println(domainEvent);
                    }

                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }





    public static class ChangePasswordUseCase extends UseCase<ChangePasswordUseCase.Request, ChangePasswordUseCase.Response>{

        private EventStoreRepository eventStoreRepository;

        public ChangePasswordUseCase(EventStoreRepository eventStoreRepository){
            this.eventStoreRepository = eventStoreRepository;
        }


        @Override
        protected void executeUseCase(Request requestValues) {
            var id = requestValues.userId;
            var newPassword = requestValues.newPassword;
            var aggregateId = new AggregateRootId(id);

            var user = User.from(aggregateId, eventStoreRepository.getEventsBy(aggregateId));
            var userPassword = new UserPassword(newPassword);
            user.updateUserPassword(userPassword);

            emit().onSuccess(new Response(user.getUncommittedChanges()));
            user.markChangesAsCommitted();

        }

        public static class Request implements UseCase.RequestValues {
            private String newPassword;
            private String userId;

            public Request(String newPassword, String userId) {
                this.newPassword = newPassword;
                this.userId = userId;
            }

        }

        public static class Response implements  UseCase.ResponseEvents {
            private List<DomainEvent> list;

            public Response(List<DomainEvent> list) {
                this.list = List.copyOf(list);
            }

            public List<DomainEvent> getDomainEvents() {
                return list;
            }
        }
    }




}
