package co.com.sofka.business.asyn;

import co.com.sofka.business.NumDomainEvent;
import co.com.sofka.business.UseCaseEventTest;
import co.com.sofka.domain.generic.DomainEvent;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.Flow;


class UseCaseExecutorTest {



    @Test
    void executorRequestUseCase() throws InterruptedException {
       newExecutor().executor(Map.of());
       Thread.sleep(1000);
    }

    public UseCaseExecutor newExecutor(){
        return new UseCaseExecutor(){
            private UseCaseEventTest.MyEventRequest request;

            @Override
            public void accept(Map<String, String> args) {
                request = new UseCaseEventTest.MyEventRequest();
            }

            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
                runUseCase(new UseCaseEventTest(), request);
            }
        }.withAggregateId("xxx-fff-vvv").withSubscriberEvent(new SubscriberEvent());
    }

    public static class SubscriberEvent implements Flow.Subscriber<DomainEvent> {

        private Flow.Subscription subscription;

        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            (this.subscription = subscription).request(2);
        }

        @Override
        public void onNext(DomainEvent item) {
            System.out.println(Thread.currentThread().getName());
            System.out.println(item.type+"/"+((NumDomainEvent)item).getNum());
            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    }
}