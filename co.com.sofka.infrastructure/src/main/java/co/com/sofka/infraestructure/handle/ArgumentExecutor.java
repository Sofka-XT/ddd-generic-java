package co.com.sofka.infraestructure.handle;

import co.com.sofka.business.asyn.UseCaseArgumentExecutor;
import co.com.sofka.business.generic.ServiceBuilder;
import co.com.sofka.business.generic.UseCase;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Logger;


/**
 * The type Argument executor.
 */
public abstract class ArgumentExecutor implements CommandHandler<Map<String, String>> {

    private static final Logger logger = Logger.getLogger(ArgumentExecutor.class.getName());
    /**
     * The Handles.
     */
    protected Map<String, Consumer<Map<String, String>>> handles = new ConcurrentHashMap<>();

    private UseCase.RequestValues request;


    /**
     * Put.
     *
     * @param type     the type
     * @param consumer the consumer
     */
    protected void put(String type, Consumer<Map<String, String>> consumer) {
        handles.put(type, consumer);
    }

    @Override
    public final void execute(Map<String, String> args) {
        var commandRequired = new CommandWrapper(
                args.get("commandType"),
                args.get("aggregateId"),
                args
        );

        if (Objects.isNull(commandRequired.getCommandType()) || commandRequired.getCommandType().isBlank()) {
            throw new IllegalArgumentException("The commandType of the aggregate must be specified");
        }

        if (Objects.isNull(commandRequired.getAggregateId()) || commandRequired.getAggregateId().isBlank()) {
            throw new IllegalArgumentException("The aggregateId of the aggregate must be specified");
        }

        var type = commandRequired.getCommandType();

        if (!handles.containsKey(type)) {
            throw new ExecutionNoFound(type);
        }

        executeCommand(commandRequired);
    }

    private void executeCommand(CommandWrapper commandWrapper) {
        logger.info("####### Executor Command #######");
        var consumer = handles.get(commandWrapper.getCommandType());
        var useCaseExecutor = (UseCaseArgumentExecutor) consumer;

        if (!Objects.isNull(commandWrapper.getAggregateId()) && !commandWrapper.getAggregateId().isBlank()) {
            useCaseExecutor.withAggregateId(commandWrapper.getAggregateId());
        }

        useCaseExecutor.executor((Map<String, String>) commandWrapper.getPayLoad());
        request = useCaseExecutor.request();
    }

    /**
     * Request use case . request values.
     *
     * @return the use case . request values
     */
    public UseCase.RequestValues request() {
        return request;
    }
}
