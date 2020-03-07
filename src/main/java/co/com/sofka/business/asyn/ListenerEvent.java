package co.com.sofka.business.asyn;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.domain.generic.DomainEvent;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;
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

    private Set<UseCase<? extends UseCase.RequestEvent, ? extends ResponseEvents>> useCases;
    private Flow.Subscription subscription;

    /**
     * Instantiates a new Listener.
     *
     * @param useCases the use cases
     */
    public ListenerEvent(Set<UseCase<? extends UseCase.RequestEvent, ? extends ResponseEvents>> useCases) {
        this.useCases = useCases;
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
        var event = Objects.requireNonNull(domainEvent);
        useCases.forEach(useCase -> {
            var useCaseCasted = (UseCase<UseCase.RequestEvent, ResponseEvents>) useCase;
            if (matchDomainEvent(event).withRequestOf(useCaseCasted)) {
                UseCaseHandler.getInstance()
                        .asyncExecutor(
                                useCaseCasted, new TriggeredEvent<>(event)
                        ).subscribe(this);
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
                if (params[0].getCanonicalName().equals(TriggeredEvent.class.getCanonicalName())) {
                    Type returnType = m.getGenericParameterTypes()[0];
                    if (returnType instanceof ParameterizedType) {
                        ParameterizedType type = (ParameterizedType) returnType;
                        matchWithAEvent = ((Class<?>) type.getActualTypeArguments()[0])
                                .getCanonicalName().equals(domainEvent.getClass()
                                        .getCanonicalName());
                    }
                }

            }
            return matchWithAEvent;
        };
    }

    /**
     * On error.
     *
     * @param throwable the throwable
     */
    @Override
    public void onError(Throwable throwable) {
        throwable.printStackTrace();
    }

    /**
     * On complete.
     */
    @Override
    public void onComplete() {
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


