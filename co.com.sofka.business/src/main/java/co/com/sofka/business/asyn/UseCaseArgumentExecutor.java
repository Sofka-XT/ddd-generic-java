package co.com.sofka.business.asyn;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.business.support.RequestCommand;
import co.com.sofka.business.support.ResponseEvents;
import co.com.sofka.domain.generic.Command;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.logging.Logger;

/**
 * The type Use case argument executor.
 *
 * @param <C> the type parameter
 */
public abstract class UseCaseArgumentExecutor<C extends Command> extends BaseUseCaseExecutor implements Consumer<Map<String, String>> {
    private static final Logger logger = Logger.getLogger(UseCaseArgumentExecutor.class.getName());

    /**
     * Register use case use case.
     *
     * @return the use case
     */
    public abstract UseCase<RequestCommand<C>, ResponseEvents> registerUseCase();


    /**
     * Register request command request command.
     *
     * @return the request command
     */
    public abstract RequestCommand<C> registerRequestCommand();

    /**
     * Executor.
     *
     * @param args    the args
     * @param headers the headers
     */
    public void executor(Map<String, String> args, Map<String, String> headers) {
        Objects.requireNonNull(args, "The command object is required");
        withHeaders(headers);
        accept(args);
        runUseCase(registerUseCase(), registerRequestCommand());
    }

    /**
     * Executor.
     *
     * @param args the args
     */
    public void executor(Map<String, String> args) {
        Objects.requireNonNull(args, "The command object is required");
        accept(args);
        runUseCase(registerUseCase(), registerRequestCommand());
    }

    private <T extends UseCase.RequestValues, R extends ResponseEvents> void runUseCase(UseCase<T, R> useCase, T request) {
        this.request = request;
        Optional.ofNullable(repository).ifPresentOrElse(useCase::addRepository, () ->
                logger.warning("No repository found for use case")
        );
        Optional.ofNullable(serviceBuilder).ifPresent(useCase::addServiceBuilder);
        Optional.ofNullable(useCases).ifPresent(useCase::addUseCases);
        Optional.ofNullable(aggregateId()).ifPresent(useCase::setIdentify);

        useCaseHandler()
                .setIdentifyExecutor(aggregateId())
                .asyncExecutor(useCase, request)
                .subscribe(subscriberEvent());
    }


}
