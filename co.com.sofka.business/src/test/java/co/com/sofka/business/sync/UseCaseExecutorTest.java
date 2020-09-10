package co.com.sofka.business.sync;


import co.com.sofka.business.UseCaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UseCaseExecutorTest {

    @Test
    void executorRequestUseCase(){
        var executor = new UseCaseExecutor<UseCaseTest.Request, UseCaseTest.Response>(){
            @Override
            public UseCaseTest.Response apply(UseCaseTest.Request request) {
                var usecase = new UseCaseTest();
                return runSynUseCase(usecase, request).orElseThrow();
            }
        }.withAggregateId("xxxx-dddd-fff");

        var res = executor.apply(new UseCaseTest.Request("testing"));
        Assertions.assertEquals("Hello wold from use case -> testing", res.getStatus());
    }
}