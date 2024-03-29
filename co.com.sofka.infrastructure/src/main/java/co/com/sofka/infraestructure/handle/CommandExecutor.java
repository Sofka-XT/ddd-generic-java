package co.com.sofka.infraestructure.handle;

import co.com.sofka.business.asyn.UseCaseCommandExecutor;
import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.generic.Command;
import co.com.sofka.infraestructure.controller.CommandWrapperSerializer;

import java.lang.reflect.ParameterizedType;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Logger;


/**
 * The type Command executor.
 */
public abstract class CommandExecutor implements CommandHandler<String> {

    private static final Logger logger = Logger.getLogger(CommandExecutor.class.getName());
    /**
     * The Handles.
     */
    protected Map<String, Consumer<Command>> handles = new ConcurrentHashMap<>();

    private UseCase.RequestValues request;


    /**
     * Put.
     *
     * @param type     the type
     * @param consumer the consumer
     */
    protected void put(String type, Consumer<Command> consumer) {
        handles.put(type, consumer);
    }

    @Override
    public final void execute(String json) {
        var commandRequired = CommandWrapperSerializer.instance().deserialize(json);

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
        var useCaseExecutor = (UseCaseCommandExecutor) consumer;

        if (!Objects.isNull(commandWrapper.getAggregateId()) || !commandWrapper.getAggregateId().isBlank()) {
            useCaseExecutor.withAggregateId(commandWrapper.getAggregateId());
        }

        useCaseExecutor.executor(commandWrapper.valueOf(
                ((ParameterizedType) useCaseExecutor.getClass().getGenericSuperclass())
                        .getActualTypeArguments()[0]
        ));
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
