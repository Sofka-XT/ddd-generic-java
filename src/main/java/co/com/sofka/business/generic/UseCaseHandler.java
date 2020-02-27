package co.com.sofka.business.generic;


import java.util.Optional;

public class UseCaseHandler {

    private static UseCaseHandler instance;

    private UseCaseHandler() {
    }

    public static UseCaseHandler getInstance() {
        if (instance == null) {
            instance = new UseCaseHandler();
        }
        return instance;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.PubEvents> SimplePublisher asyncExecutor(
            final UseCase<T, R> useCase, T values) {

        SimplePublisher publisher = new SimplePublisher();
        useCase.setRequest(values);
        useCase.setUseCaseCallback((UseCase.UseCaseFormat<R>) publisher);
        useCase.run();
        return publisher;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValues> Optional<R> syncExecutor(
            final UseCase<T, R> useCase, T values) {

        SimpleResponse<R> simpleResponse = new SimpleResponse<>();
        useCase.setRequest(values);
        useCase.setUseCaseCallback(simpleResponse);
        useCase.run();
        if(simpleResponse.hasError()){
            throw simpleResponse.exception;
        }
       return Optional.of(simpleResponse.response);
    }
}