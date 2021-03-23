package co.com.sofka.business.generic;

/**
 * The type Use case response.
 *
 * @param <R> the type parameter
 */
public final class UseCaseResponse<R extends UseCase.ResponseValues> implements UseCase.UseCaseFormat<R> {
    /**
     * The Response.
     */
    protected R response;
    /**
     * The Exception.
     */
    protected RuntimeException exception;

    @Override
    public void onResponse(R response) {
        this.response = response;
    }

    @Override
    public void onError(RuntimeException exception) {
        this.exception = exception;
    }

    /**
     * Has error boolean.
     *
     * @return the boolean
     */
    public boolean hasError() {
        return exception != null;
    }
}
