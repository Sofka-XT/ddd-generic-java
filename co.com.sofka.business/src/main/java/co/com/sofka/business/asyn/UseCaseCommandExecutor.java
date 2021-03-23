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
 * The type Use case command executor.
 *
 * @param <C> the type parameter
 */
public abstract class UseCaseCommandExecutor<C extends Command> extends BaseUseCaseExecutor implements Consumer<C> {
    private static final Logger logger = Logger.getLogger(UseCaseCommandExecutor.class.getName());

    private C command;

    /**
     * Register use case use case.
     *
     * @return the use case
     */
    public abstract UseCase<RequestCommand<C>, ResponseEvents> registerUseCase();

    /**
     * Executor.
     *
     * @param command the command
     * @param headers the headers
     */
    public void executor(C command, Map<String, String> headers) {
        this.command = Objects.requireNonNull(command, "The command object is required");
        withHeaders(headers);
        accept(command);
        runUseCase(registerUseCase());
    }

    /**
     * Executor.
     *
     * @param command the command
     */
    public void executor(C command) {
        this.command = Objects.requireNonNull(command, "The command object is required");
        accept(command);
        runUseCase(registerUseCase());
    }


    /**
     * Run use case.
     *
     * @param <T>     the type parameter
     * @param <R>     the type parameter
     * @param useCase the use case
     */
    public <T extends UseCase.RequestValues, R extends ResponseEvents> void runUseCase(UseCase<T, R> useCase) {
        var request = (T) (new RequestCommand<>(command));
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
