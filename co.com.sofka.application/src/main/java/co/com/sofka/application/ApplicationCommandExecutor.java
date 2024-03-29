package co.com.sofka.application;

import co.com.sofka.application.init.InitializerCommandExecutor;
import co.com.sofka.business.asyn.UseCaseCommandExecutor;
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
 * The type Application command executor.
 */
public class ApplicationCommandExecutor extends InitializerCommandExecutor {

    private static final Logger logger = Logger.getLogger(ApplicationCommandExecutor.class.getName());

    /**
     * Instantiates a new Application command executor.
     *
     * @param packageUseCase  the package use case
     * @param subscriberEvent the subscriber event
     * @param repository      the repository
     */
    public ApplicationCommandExecutor(String packageUseCase, SubscriberEvent subscriberEvent, EventStoreRepository repository) {
        super(packageUseCase, subscriberEvent, repository);
    }

    @Override
    public void addHandle(ClassInfo handleClassInfo, ServiceBuilder serviceBuilder, String aggregate, String type) {
        UseCaseCommandExecutor handle;
        try {
            handle = (UseCaseCommandExecutor) handleClassInfo.loadClass().getDeclaredConstructor().newInstance();
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
            put(type, (UseCaseCommandExecutor) baseUseCaseExecutor);
            var message = String.format("@@@@ %s Registered handle command with type --> %s", aggregate, type);
            logger.info(message);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            logger.log(Level.SEVERE, String.format("There is a error inside register command type -->%s", type), e);
        }
    }


}
