package co.com.sofka.business.generic;

import java.util.concurrent.CompletableFuture;

public class UseCaseHandler {

    private static UseCaseHandler INSTANCE;

    private UseCaseHandler() {
    }

    public static UseCaseHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UseCaseHandler();
        }
        return INSTANCE;
    }

    public <T extends UseCase.RequestValues, R extends UseCase.ResponseValue> CompletableFuture<R> execute(
            final UseCase<T, R> useCase, T values) {
        CompletableFuture<R> completableFuture = new CompletableFuture<>();
        useCase.setRequestValues(values);
        useCase.setUseCaseCallback(new ControllerFormatWrapper<>(completableFuture));
        useCase.run();
        return completableFuture;
    }


    private static final class ControllerFormatWrapper<V extends UseCase.ResponseValue> implements
            UseCase.UseCaseFormat<V> {
        private final CompletableFuture<V> publish;

        private ControllerFormatWrapper(CompletableFuture<V> publish) {
            this.publish = publish;
        }

        @Override
        public void onSuccess(V response) {
            publish.complete(response);
        }

        @Override
        public void onError(RuntimeException e) {

            publish.completeExceptionally(e);
        }
    }
}