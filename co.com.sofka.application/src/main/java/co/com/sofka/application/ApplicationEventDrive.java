package co.com.sofka.application;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.annotation.ExtensionService;
import co.com.sofka.business.generic.BusinessException;
import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.business.support.TriggeredEvent;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.asyn.SubscriberEvent;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import io.github.classgraph.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Application Event Drive
 * <p>
 * In this class implement a mechanism to execute
 * a UseCaseHandler#asyncExecutor to execute use cases asynchronously.
 * </p>
 */
public class ApplicationEventDrive {
    private static final Logger logger = Logger.getLogger(ApplicationEventDrive.class.getName());
    private final Set<UseCase.UseCaseWrap> useCases;
    private final SubscriberEvent subscriberEvent;
    private final EventStoreRepository repository;
    private final String packageUseCase;

    public ApplicationEventDrive(String packageUseCase, SubscriberEvent subscriberEvent) {
        this(packageUseCase, subscriberEvent, null);
    }

    public ApplicationEventDrive(String packageUseCase, SubscriberEvent subscriberEvent, EventStoreRepository repository) {
        this.subscriberEvent = subscriberEvent;
        this.packageUseCase = packageUseCase;
        this.repository = repository;
        this.useCases = new HashSet<>();
        initialize();
    }

    private void initialize() {
        logger.info("---- Registered Event Listener Use Case -----");
        try (ScanResult result = new ClassGraph()
                .enableAllInfo()
                .whitelistPackages(packageUseCase)
                .scan()) {
            ClassInfoList classInfos = result.getClassesWithAnnotation(EventListener.class.getName());
            classInfos.parallelStream().forEach(handleClassInfo -> {
                try {
                    AnnotationInfo annotationInfo = handleClassInfo.getAnnotationInfo(EventListener.class.getName());
                    String type = getEventType(annotationInfo);
                    var usecase = (UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents>) handleClassInfo
                            .loadClass()
                            .getDeclaredConstructor().newInstance();
                    usecase.addServiceBuilder(getServiceBuilder(handleClassInfo));
                    useCases.add(new UseCase.UseCaseWrap(type, usecase));
                    logger.info("@@@@ Registered use case for event lister --> " + usecase.getClass().getSimpleName() + "[" + type + "]");
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) {
                }
            });
        }
    }

    private String getEventType(AnnotationInfo annotationInfo) {
        return Optional.ofNullable(annotationInfo).map(annotation -> {
            AnnotationParameterValueList paramVals = annotation.getParameterValues();
            return (String) paramVals.getValue("eventType");
        }).orElseThrow();
    }

    /**
     * This method triggers a use case registered as @EventListener().
     *
     * @param domainEvent event from bus or use case
     */
    public final void fire(DomainEvent domainEvent) {
        var event = Objects.requireNonNull(domainEvent);
        useCases.stream()
                .filter(useCaseWrap -> useCaseWrap.eventType().equals(domainEvent.type))
                .forEach(useCaseWrap -> {
                    var useCase = useCaseWrap.usecase();
                    useCase.addRepository(new DomainEventRepository() {
                        @Override
                        public List<DomainEvent> getEventsBy(String aggregateRootId) {
                            return repository.getEventsBy(event.getAggregateName(), aggregateRootId);
                        }

                        @Override
                        public List<DomainEvent> getEventsBy(String aggregate, String aggregateRootId) {
                            return repository.getEventsBy(aggregate, aggregateRootId);
                        }
                    });
                    logger.info("Use case handler to event --> " + event.type);
                    UseCaseHandler.getInstance()
                            .asyncExecutor(
                                    useCase, new TriggeredEvent<>(event)
                            ).subscribe(subscriberEvent);
                });
    }

    /**
     * request an useCase by domain event
     *
     * @param domainEvent triggered
     * @return ResponseEvents Optional
     */
    public final Optional<ResponseEvents> requestUseCase(DomainEvent domainEvent) {
        var event = Objects.requireNonNull(domainEvent);
        var wrap = useCases.stream()
                .filter(useCaseWrap -> useCaseWrap.eventType().equals(domainEvent.type))
                .findFirst().orElseThrow(() -> new BusinessException(domainEvent.aggregateRootId(), "The use case event listener not registered"));
        return UseCaseHandler.getInstance()
                .syncExecutor(wrap.usecase(), new TriggeredEvent<>(event));
    }

    private ServiceBuilder getServiceBuilder(ClassInfo handleClassInfo) {
        AnnotationInfo annotationInfo = handleClassInfo.getAnnotationInfo(ExtensionService.class.getName());
        return Optional.ofNullable(annotationInfo).map(annotation -> {
            ServiceBuilder serviceBuilder = new ServiceBuilder();
            AnnotationParameterValueList paramVals = annotation.getParameterValues();
            var list = (Object[]) paramVals.getValue("value");
            Stream.of(list).forEach(className -> {
                try {
                    var ref = (AnnotationClassRef) className;
                    serviceBuilder.addService(ref.loadClass().getDeclaredConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    logger.log(Level.SEVERE, "ERROR over service builder", e);
                    throw new ServiceBuildException(e);
                }
            });
            return serviceBuilder;
        }).orElse(new ServiceBuilder());
    }
}
