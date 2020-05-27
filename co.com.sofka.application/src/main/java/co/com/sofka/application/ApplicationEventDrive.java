package co.com.sofka.application;

import co.com.sofka.business.annotation.EventListener;
import co.com.sofka.business.annotation.ExtensionService;
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
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ApplicationEventDrive {
    private final Set<UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents>> useCases;
    private static final Logger logger = Logger.getLogger(ApplicationEventDrive.class.getName());
    private final SubscriberEvent subscriberEvent;
    private final DomainEventRepository repository;
    private final String packageUseCase;

    public ApplicationEventDrive(String packageUseCase, SubscriberEvent subscriberEvent){
        this(packageUseCase, subscriberEvent, null);
    }

    public ApplicationEventDrive(String packageUseCase, SubscriberEvent subscriberEvent, DomainEventRepository repository){
        this.subscriberEvent = subscriberEvent;
        this.packageUseCase = packageUseCase;
        this.repository = repository;
        this.useCases = new HashSet<>();
        initialize();
    }

    private void initialize(){
        logger.info("---- Registered Event Listener Use Case -----");
        try (ScanResult result = new ClassGraph()
                .enableAllInfo()
                .whitelistPackages(packageUseCase)
                .scan()) {
            ClassInfoList classInfos = result.getClassesWithAnnotation(EventListener.class.getName());
            classInfos.parallelStream().forEach(handleClassInfo -> {
                try {
                    var usecase = (UseCase<TriggeredEvent<? extends DomainEvent>,ResponseEvents>)handleClassInfo
                            .loadClass()
                            .getDeclaredConstructor().newInstance();
                    usecase.addServiceBuilder(getServiceBuilder(handleClassInfo));
                    usecase.addRepository(repository);
                    useCases.add(usecase);
                    logger.info("@@@@ Registered use case for event lister --> " + usecase.getClass().getName());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException ignored) { }
            });
        }
    }

    public final void fire(DomainEvent domainEvent) {
        var event = Objects.requireNonNull(domainEvent);
        useCases.forEach(useCase -> {
            var useCaseCasted = (UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents>) useCase;
            if (matchDomainEvent(event).withRequestOf(useCaseCasted)) {
                UseCaseHandler.getInstance()
                        .asyncExecutor(
                                useCaseCasted, new TriggeredEvent<>(event)
                        ).subscribe(subscriberEvent);
            }
        });

    }

    private ServiceBuilder getServiceBuilder(ClassInfo handleClassInfo) {
        AnnotationInfo annotationInfo = handleClassInfo.getAnnotationInfo(ExtensionService.class.getName());
        return Optional.ofNullable(annotationInfo).map(annotation -> {
            ServiceBuilder serviceBuilder = new ServiceBuilder();
            AnnotationParameterValueList paramVals = annotation.getParameterValues();
            var list = (Object[]) paramVals.getValue("value");
            Stream.of(list).forEach(className -> {
                try {
                    var ref = (AnnotationClassRef)className;
                    serviceBuilder.addService(ref.loadClass().getDeclaredConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    logger.log(Level.SEVERE, "ERROR over service builder", e);
                    throw new ServiceBuildException(e);
                }
            });
            return serviceBuilder;
        }).orElse(new ServiceBuilder());
    }

    private boolean isMatchWithAEvent(DomainEvent domainEvent, Method m, Class<?>[] params) {
        boolean matchWithAEvent = false;
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

    private ConditionalRequestUseCase matchDomainEvent(DomainEvent domainEvent) {
        return useCaseCasted -> {
            var useCase = Objects.requireNonNull(useCaseCasted);
            String target = "executeUseCase";
            boolean matchWithAEvent = false;
            Method m = null;
            try {
                m = useCase.getClass().getMethod("executeUseCase", TriggeredEvent.class);
                if (target.equals(m.getName())) {
                    Class<?>[] params = m.getParameterTypes();
                    matchWithAEvent = isMatchWithAEvent(domainEvent, m, params);
                }
            } catch (NoSuchMethodException ignored) {
            }

            return matchWithAEvent;
        };
    }

    @FunctionalInterface
    private interface ConditionalRequestUseCase {
        /**
         * With request of boolean.
         *
         * @param useCaseCasted the use case casted
         * @return the boolean
         */
        boolean withRequestOf(UseCase<TriggeredEvent<? extends DomainEvent>, ResponseEvents> useCaseCasted);
    }
}
