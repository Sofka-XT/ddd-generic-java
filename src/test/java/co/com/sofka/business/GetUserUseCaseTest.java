package co.com.sofka.business;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.events.UserCreated;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;

public class GetUserUseCaseTest {


    public static class GetUserUseCase extends UseCase<GetUserUseCase.Request, GetUserUseCase.Response> {


        @Override
        protected void executeUseCase(Request requestValues) {

        }

        public static class Request implements UseCase.SubEvent {

            private final UserCreated event;

            public Request(UserCreated event) {
                this.event = event;
            }

            @Override
            public UserCreated getDomainEvent() {
                return event;
            }
        }

        public static class Response implements UseCase.PubEvents {
            @Override
            public List<DomainEvent> getDomainEvents() {
                return null;
            }
        }
    }
}
