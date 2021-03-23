package co.com.sofka.application.init;

import co.com.sofka.business.annotation.CommandHandles;
import co.com.sofka.business.annotation.CommandType;
import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.infraestructure.asyn.SubscriberEvent;
import co.com.sofka.infraestructure.handle.ArgumentExecutor;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import io.github.classgraph.*;

import java.util.logging.Logger;

import static co.com.sofka.application.init.BaseApplicationExecutor.getAggregate;
import static co.com.sofka.application.init.BaseApplicationExecutor.getCommandType;


/**
 * The type Initializer argument executor.
 */
public abstract class InitializerArgumentExecutor extends ArgumentExecutor {
    private static final Logger logger = Logger.getLogger(InitializerArgumentExecutor.class.getName());
    /**
     * The Subscriber event.
     */
    protected final SubscriberEvent subscriberEvent;
    /**
     * The Repository.
     */
    protected final EventStoreRepository repository;

    protected final ServiceBuilder serviceBuilder;

    /**
     * Instantiates a new Initializer argument executor.
     *
     * @param packageUseCase  the package use case
     * @param subscriberEvent the subscriber event
     * @param repository      the repository
     */
    protected InitializerArgumentExecutor(
            String packageUseCase,
            SubscriberEvent subscriberEvent,
            EventStoreRepository repository) {
        this.subscriberEvent = subscriberEvent;
        this.repository = repository;
        this.serviceBuilder = new ServiceBuilder();
        logger.info("---- Registered Commands -----");
        try (ScanResult result = new ClassGraph()
                .enableAllInfo()
                .whitelistPackages(packageUseCase)
                .scan()) {
            ClassInfoList classInfos = result.getClassesWithAnnotation(CommandHandles.class.getName());
            classInfos.parallelStream().forEach(handleClassInfo -> {
                AnnotationInfo annotationInfo = handleClassInfo.getAnnotationInfo(CommandType.class.getName());
                String type = getCommandType(packageUseCase, handleClassInfo, annotationInfo);
                String aggregate = getAggregate(annotationInfo);
                addHandle(handleClassInfo, serviceBuilder, aggregate, type);
            });
        }
    }

    /**
     * Add handle.
     *
     * @param handleClassInfo the handle class info
     * @param aggregate       the aggregate
     * @param type            the type
     */
    public abstract void addHandle(ClassInfo handleClassInfo, ServiceBuilder serviceBuilder, String aggregate, String type);


}
