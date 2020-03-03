package co.com.sofka.business;

import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.domain.generic.DomainEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Flow;

public class UserCreateUseCaseTest {

    private UserCreateUseCase useCase;
    public UserCreateUseCaseTest(){
        this.useCase = new UserCreateUseCase();
    }
    @Test
    public void createUser(){
        UserCreateUseCase.Request request = new UserCreateUseCase.Request();
        UseCaseHandler.getInstance()
                .asyncExecutor(useCase, request)
                .subscribe(new Flow.Subscriber<DomainEvent>() {
                    @Override
                    public void onSubscribe(Flow.Subscription subscription) {

                    }

                    @Override
                    public void onNext(DomainEvent event) {
                        Assertions.assertEquals("user.created", event.type);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Assertions.fail("A problem inside usecase");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
