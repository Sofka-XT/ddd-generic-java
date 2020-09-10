package co.com.sofka.business.asyn;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.generic.DomainEvent;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.SubmissionPublisher;
import java.util.logging.Logger;

/**
 * The type Publisher event.
 * <p>
 * With this class, events are published asynchronously, supported by a thread pool.
 */
public final class PublisherEvent extends SubmissionPublisher<DomainEvent> implements UseCase.UseCaseFormat<ResponseEvents> {

    private static final Logger logger = Logger.getLogger(PublisherEvent.class.getName());

    public PublisherEvent(){
        super(ForkJoinPool.commonPool(), 2);
    }

    @Override
    public void onSuccess(ResponseEvents responseEvents) {
        responseEvents.getDomainEvents().forEach(event -> {
            logger.info("Publish -> " + event.type + " ### " + event.getAggregateName()+"/"+event.aggregateRootId());
            submit(event);
        });
        close();
    }

    @Override
    public void onError(RuntimeException exception) {
        getSubscribers().forEach(sub -> sub.onError(exception));
        close();
    }


}
