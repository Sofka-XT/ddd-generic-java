package co.com.sofka.application;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.annotation.ExtensionService;
import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.infraestructure.asyn.SubscriberEvent;
import co.com.sofka.infraestructure.handle.CommandExecutor;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import io.github.classgraph.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Application command executor
 * <p>
 * This class is implemented to execute the commands from a controller.
 * Use this class within your Rest Controller or your service to execute a use case.
 */
public class ApplicationCommandExecutor extends CommandExecutor {
    private static final Logger logger = Logger.getLogger(ApplicationCommandExecutor.class.getName());
    private final SubscriberEvent subscriberEvent;
    private final EventStoreRepository repository;

    private final String packageUseCase;


    /**
     * Construct to new application command
     *
     * @param packageUseCase  the path package of the use cases
     * @param subscriberEvent the subscriber to async process
     * @param repository      the repository to store event
     */
    public ApplicationCommandExecutor(
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
                    .withDomainEventRepo(aggregateRootId -> repository.getEventsBy(aggregate, aggregateRootId)));
            var message = String.format("@@@@ %s Registered handle command with type --> %s", aggregate, type);
            logger.info(message);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.SEVERE, String.format("There is a error inside register command type -->%s", type), e);
        }
    }

}
