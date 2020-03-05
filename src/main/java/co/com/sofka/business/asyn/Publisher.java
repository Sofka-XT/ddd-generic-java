package co.com.sofka.business.asyn;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.concurrent.SubmissionPublisher;

/**
 * The type Publisher.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public final class Publisher extends SubmissionPublisher<DomainEvent> implements UseCase.UseCaseFormat<ResponseEvents> {

    /**
     * Instantiates a new Publisher.
     */
    public Publisher() {
        super();
    }

    /**
     * On success.
     *
     * @param responseEvents the response events
     */
    @Override
    public void onSuccess(ResponseEvents responseEvents) {
        responseEvents.getDomainEvents().forEach(this::submit);
        close();
    }

    /**
     * On error.
     *
     * @param exception the exception
     */
    @Override
    public void onError(RuntimeException exception) {
        getSubscribers().forEach(sub -> sub.onError(exception));
        close();
    }


}
