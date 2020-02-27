package co.com.sofka.business.generic;

public final class SimpleResponse<R extends UseCase.ResponseValues> implements UseCase.UseCaseFormat<R>{
    protected R response;
    protected RuntimeException exception;

    @Override
    public void onSuccess(R response) {
        this.response = response;
    }

    @Override
    public void onError(RuntimeException exception) {
        this.exception = exception;
    }

    public boolean hasError(){
        return exception != null;
    }
}
