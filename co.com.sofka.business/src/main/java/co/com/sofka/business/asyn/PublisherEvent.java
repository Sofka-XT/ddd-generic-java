package co.com.sofka.business.asyn;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.generic.DomainEvent;


import java.util.concurrent.SubmissionPublisher;

/**
 * The type Publisher event.
 */
public final class PublisherEvent extends SubmissionPublisher<DomainEvent> implements UseCase.UseCaseFormat<ResponseEvents> {

    @Override
    public void onSuccess(ResponseEvents responseEvents) {
        responseEvents.getDomainEvents().forEach(this::submit);
        close();
    }

    @Override
    public void onError(RuntimeException exception) {
        getSubscribers().forEach(sub -> sub.onError(exception));
        close();
    }


}
