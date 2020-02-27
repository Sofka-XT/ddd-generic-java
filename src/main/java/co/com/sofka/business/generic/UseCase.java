package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.DomainEvent;

import java.util.List;

public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValues> {

    private Q request;

    private UseCaseFormat<P> useCaseFormat;

    protected Q request() {
        return request;
    }

    protected void setRequest(Q request) {
        this.request = request;
    }

    protected UseCaseFormat<P> emit() {
        return useCaseFormat;
    }

    protected void setUseCaseCallback(UseCaseFormat<P> useCaseFormat) {
        this.useCaseFormat = useCaseFormat;
    }


    protected void run() {
        try {
            executeUseCase(request);
        } catch (RuntimeException e) {
            useCaseFormat.onError(e);
        }
    }

    protected abstract void executeUseCase(Q objectInput);



    public interface PubEvents extends ResponseValues {
         List<DomainEvent> getDomainEvents();
    }

    public interface SubEvent extends RequestValues {
        DomainEvent getDomainEvent();
    }

    public interface ResponseValues {
    }

    public interface RequestValues {

    }

    public interface UseCaseFormat<R> {
        void onSuccess(R output);

        void onError(RuntimeException e);
    }
}
