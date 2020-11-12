package co.com.sofka.application;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.annotation.ExtensionService;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.asyn.SubscriberEvent;
import co.com.sofka.infraestructure.handle.AsyncCommandExecutor;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import io.github.classgraph.*;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * The type Application command executor.
 */
public class ApplicationAsyncCommandExecutor extends AsyncCommandExecutor {
    private static final Logger logger = Logger.getLogger(ApplicationAsyncCommandExecutor.class.getName());
    private final SubscriberEvent subscriberEvent;
    private final EventStoreRepository repository;

    private final String packageUseCase;


    /**
     * Instantiates a new Application command executor.
     *
     * @param packageUseCase  the package use case
     * @param subscriberEvent the subscriber event
     * @param repository      the repository
     */
    public ApplicationAsyncCommandExecutor(
            String packageUseCase,
            SubscriberEvent subscriberEvent,
            EventStoreRepository repository) {
        this.subscriberEvent = subscriberEvent;
        this.repository = repository;
        this.packageUseCase = packageUseCase;
        initialize();
    }

    private void initialize() {
        logger.info("---- Registered Commands -----");
        try (ScanResult result = new ClassGraph()
                .enableAllInfo()
                .whitelistPackages(packageUseCase)
                .scan()) {
            ClassInfoList classInfos = result.getClassesWithAnnotation(CommandHandles.class.getName());
            classInfos.parallelStream().forEach(handleClassInfo -> {
                AnnotationInfo annotationInfo = handleClassInfo.getAnnotationInfo(CommandType.class.getName());
                String type = getCommandType(handleClassInfo, annotationInfo);
                String aggregate = getAggregate(annotationInfo);
                addHandle(handleClassInfo, aggregate, type);
            });
        }
    }

    private String getCommandType(ClassInfo handleClassInfo, AnnotationInfo annotationInfo) {
        return Optional.ofNullable(annotationInfo).map(annotation -> {
            AnnotationParameterValueList paramVals = annotation.getParameterValues();
            return (String) paramVals.getValue("name");
        }).orElse(handleClassInfo
                .loadClass()
                .getCanonicalName()
                .toLowerCase().replace(packageUseCase + ".", "")
        );
    }

    private String getAggregate(AnnotationInfo annotationInfo) {
        return Optional.ofNullable(annotationInfo).map(annotation -> {
            AnnotationParameterValueList paramVals = annotation.getParameterValues();
            return (String) paramVals.getValue("aggregate");
        }).orElse("default");
    }

    private ServiceBuilder getServiceBuilder(ClassInfo handleClassInfo) {
        AnnotationInfo annotationInfo = handleClassInfo.getAnnotationInfo(ExtensionService.class.getName());
        ServiceBuilder serviceBuilder = new ServiceBuilder();
        return Optional.ofNullable(annotationInfo).map(annotation -> {
            AnnotationParameterValueList paramVals = annotation.getParameterValues();
            var list = (Object[]) paramVals.getValue("value");
            for (Object o : list) {
                try {
                    var ref = (AnnotationClassRef) o;
                    serviceBuilder.addService(ref.loadClass().getDeclaredConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    logger.log(Level.SEVERE, "ERROR over service builder", e);
                    throw new ServiceBuildException(e);
                }
            }
            return serviceBuilder;
        }).orElse(serviceBuilder);
    }

    private void addHandle(ClassInfo handleClassInfo, String aggregate, String type) {
        UseCaseExecutor handle;
        try {
            handle = (UseCaseExecutor) handleClassInfo.loadClass().getDeclaredConstructor().newInstance();
            put(type, handle
                    .withSubscriberEvent(subscriberEvent)
                    .withUseCaseHandler(UseCaseHandler.getInstance())
                    .withServiceBuilder(getServiceBuilder(handleClassInfo))
                    .withDomainEventRepo(new DomainEventRepository() {
                        @Override
                        public List<DomainEvent> getEventsBy(String aggregateRootId) {
                            return repository.getEventsBy(aggregate, aggregateRootId);
                        }

                        @Override
                        public List<DomainEvent> getEventsBy(String aggregate, String aggregateRootId) {
                            return repository.getEventsBy(aggregate, aggregateRootId);
                        }
                    }));
            var message = String.format("@@@@ %s Registered handle command with type --> %s", aggregate, type);
            logger.info(message);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.SEVERE, String.format("There is a error inside register command type -->%s", type), e);
        }
    }

}
