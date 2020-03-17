package co.com.sofka.business.asyn;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.domain.generic.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Flow;


/**
 * The type Listener.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public abstract class ListenerEvent implements Flow.Subscriber<DomainEvent> {
    private static final Logger logger = LoggerFactory.getLogger(ListenerEvent.class);

    private final Set<UseCase<? extends UseCase.RequestEvent, ? extends ResponseEvents>> useCases;
    private Flow.Subscription subscription;
    private Flow.Subscriber<DomainEvent> subscriber;

    /**
     * Instantiates a new Listener.
     *
     * @param useCases the use cases
     */
    public ListenerEvent(Set<UseCase<? extends UseCase.RequestEvent, ? extends ResponseEvents>> useCases) {
        this.useCases = useCases;
    }


    /**
     * Subscriber flow . subscriber.
     *
     * @return the flow . subscriber
     */
    public Flow.Subscriber<DomainEvent> subscriber() {
        return Optional.ofNullable(subscriber).orElse(this);
    }

    /**
     * Sets subscriber.
     *
     * @param subscriber the subscriber
     */
    public void setSubscriber(Flow.Subscriber<DomainEvent> subscriber) {
        this.subscriber = subscriber;
    }

    /**
     * On subscribe.
     *
     * @param subscription the subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        this.subscription = subscription;
        this.subscription.request(1);
    }

    /**
     * On next.
     *
     * @param domainEvent the domain event
     */
    @Override
    public final void onNext(DomainEvent domainEvent) {
        logger.debug(String.format("onNext[%s]", domainEvent.type));
        var event = Objects.requireNonNull(domainEvent);
        useCases.forEach(useCase -> {
            var useCaseCasted = (UseCase<UseCase.RequestEvent, ResponseEvents>) useCase;
            if (matchDomainEvent(event).withRequestOf(useCaseCasted)) {
                UseCaseHandler.getInstance()
                        .asyncExecutor(
                                useCaseCasted, new TriggeredEvent<>(event)
                        ).subscribe(subscriber());
            }
            subscription.request(1);
        });

    }

    private ConditionalRequestUseCase matchDomainEvent(DomainEvent domainEvent) {
        return useCaseCasted -> {
            var useCase = Objects.requireNonNull(useCaseCasted);
            String target = "executeUseCase";
            boolean matchWithAEvent = false;
            Method m = useCase.getClass().getDeclaredMethods()[0];
            if (target.equals(m.getName())) {
                Class<?>[] params = m.getParameterTypes();
                matchWithAEvent = isMatchWithAEvent(domainEvent, matchWithAEvent, m, params);
            }
            return matchWithAEvent;
        };
    }

    private boolean isMatchWithAEvent(DomainEvent domainEvent, boolean matchWithAEvent, Method m, Class<?>[] params) {
        if (params[0].getCanonicalName().equals(TriggeredEvent.class.getCanonicalName())) {
            Type returnType = m.getGenericParameterTypes()[0];
            if (returnType instanceof ParameterizedType) {
                ParameterizedType type = (ParameterizedType) returnType;
                matchWithAEvent = ((Class<?>) type.getActualTypeArguments()[0])
                        .getCanonicalName().equals(domainEvent.getClass()
                                .getCanonicalName());
            }
        }
        return matchWithAEvent;
    }

    /**
     * On error.
     *
     * @param throwable the throwable
     */
    @Override
    public void onError(Throwable throwable) {
        logger.error(String.format("onError[%s]", throwable.getMessage()));
    }

    /**
     * On complete.
     */
    @Override
    public void onComplete() {
        logger.debug(String.format("onComplete[%s]", this.getClass().getSimpleName()));
    }

    @FunctionalInterface
    private interface ConditionalRequestUseCase {
        /**
         * With request of boolean.
         *
         * @param useCaseCasted the use case casted
         * @return the boolean
         */
        boolean withRequestOf(UseCase<UseCase.RequestEvent, ResponseEvents> useCaseCasted);
    }

}


