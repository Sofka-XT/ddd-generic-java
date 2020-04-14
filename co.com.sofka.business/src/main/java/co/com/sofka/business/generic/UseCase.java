package co.com.sofka.business.generic;

import co.com.sofka.domain.generic.DomainEvent;


/**
 * The type Use case.
 *
 * @param <Q> the type parameter
 * @param <P> the type parameter
 */
public abstract class UseCase<Q extends UseCase.RequestValues, P extends UseCase.ResponseValues> {

    private Q request;

    private UseCaseFormat<P> useCaseFormat;

    /**
     * Request q.
     *
     * @return the q
     */
    protected Q request() {
        return request;
    }

    /**
     * Sets request.
     *
     * @param request the request
     */
    protected void setRequest(Q request) {
        this.request = request;
    }

    /**
     * Emit use case format.
     *
     * @return the use case format
     */
    protected UseCaseFormat<P> emit() {
        return useCaseFormat;
    }

    /**
     * Sets use case callback.
     *
     * @param useCaseFormat the use case format
     */
    protected void setUseCaseCallback(UseCaseFormat<P> useCaseFormat) {
        this.useCaseFormat = useCaseFormat;
    }


    /**
     * Run.
     */
    protected void run() {
        try {
            executeUseCase(request);
        }
        catch (BusinessException e){
            useCaseFormat.onError(e);
        } catch (RuntimeException e) {
            var exception = new UnexpectedException("There is an unexpected problem in the use case", e);
            useCaseFormat.onError(exception);
        }
    }

    /**
     * Execute use case.
     *
     * @param objectInput the object input
     */
    protected abstract void executeUseCase(Q objectInput);

    /**
     * The interface Request event.
     */
    public interface RequestEvent extends RequestValues {
        /**
         * Gets domain event.
         *
         * @return the domain event
         */
        DomainEvent getDomainEvent();
    }

    /**
     * The interface Response values.
     */
    public interface ResponseValues {
    }

    /**
     * The interface Request values.
     */
    public interface RequestValues {
    }

    /**
     * The interface Use case format.
     *
     * @param <R> the type parameter
     */
    public interface UseCaseFormat<R> {
        /**
         * On success.
         *
         * @param output the output
         */
        void onSuccess(R output);

        /**
         * On error.
         *
         * @param exception the exception
         */
        void onError(RuntimeException exception);
    }

}
