package co.com.sofka.application;

import co.com.sofka.application.init.InitializerArgumentExecutor;
import co.com.sofka.business.asyn.UseCaseArgumentExecutor;
import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCaseHandler;
import co.com.sofka.business.repository.DomainEventRepository;
import co.com.sofka.domain.generic.DomainEvent;
import co.com.sofka.infraestructure.asyn.SubscriberEvent;
import co.com.sofka.infraestructure.repository.EventStoreRepository;
import io.github.classgraph.ClassInfo;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static co.com.sofka.application.init.BaseApplicationExecutor.getServiceBuilder;


/**
 * The type Application argument executor.
 */
public class ApplicationArgumentExecutor extends InitializerArgumentExecutor {

    private static final Logger logger = Logger.getLogger(ApplicationArgumentExecutor.class.getName());

    /**
     * Instantiates a new Application argument executor.
     *
     * @param packageUseCase  the package use case
     * @param subscriberEvent the subscriber event
     * @param repository      the repository
     */
    public ApplicationArgumentExecutor(String packageUseCase, SubscriberEvent subscriberEvent, EventStoreRepository repository) {
        super(packageUseCase, subscriberEvent, repository);
    }

    @Override
    public void addHandle(ClassInfo handleClassInfo, ServiceBuilder serviceBuilder, String aggregate, String type) {
        UseCaseArgumentExecutor handle;
        try {
            handle = (UseCaseArgumentExecutor) handleClassInfo.loadClass().getDeclaredConstructor().newInstance();
            var baseUseCaseExecutor = handle.withSubscriberEvent(subscriberEvent)
                    .withUseCaseHandler(UseCaseHandler.getInstance())
                    .withServiceBuilder(getServiceBuilder(serviceBuilder, handleClassInfo))
                    .withDomainEventRepo(new DomainEventRepository() {
                        @Override
                        public List<DomainEvent> getEventsBy(String aggregateRootId) {
                            return repository.getEventsBy(aggregate, aggregateRootId);
                        }

                        @Override
                        public List<DomainEvent> getEventsBy(String aggregate, String aggregateRootId) {
                            return repository.getEventsBy(aggregate, aggregateRootId);
                        }
                    });
            put(type, (UseCaseArgumentExecutor) baseUseCaseExecutor);
            var message = String.format("@@@@ %s Registered handle command with type --> %s", aggregate, type);
            logger.info(message);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.SEVERE, String.format("There is a error inside register command type -->%s", type), e);
        }
    }


}
