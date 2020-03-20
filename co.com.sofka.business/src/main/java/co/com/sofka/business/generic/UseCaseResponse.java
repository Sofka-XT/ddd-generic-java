package co.com.sofka.business.generic;

/**
 * The type Use case response.
 *
 * @param <R> the type parameter
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
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

    /**
     * On success.
     *
     * @param response the response
     */
    @Override
    public void onSuccess(R response) {
        this.response = response;
    }

    /**
     * On error.
     *
     * @param exception the exception
     */
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
