package co.com.sofka.business.asyn;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.generic.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.SubmissionPublisher;

/**
 * The type publisher of the event.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public final class PublisherEvent extends SubmissionPublisher<DomainEvent> implements UseCase.UseCaseFormat<ResponseEvents> {

    private static final Logger logger = LoggerFactory.getLogger(PublisherEvent.class);

    /**
     * On success.
     *
     * @param responseEvents the response events
     */
    @Override
    public void onSuccess(ResponseEvents responseEvents) {
        logger.debug(String.format("onSuccess[%s]", responseEvents.getDomainEvents()));
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
        logger.debug(String.format("onError[%s]", exception.getMessage()));
        getSubscribers().forEach(sub -> sub.onError(exception));
        close();
    }


}
