package co.com.sofka.infraestructure.handle;

import co.com.sofka.business.sync.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Logger;


/**
 * The type Command executor.
 */
public abstract class SyncCommandExecutor<T extends UseCase.RequestValues, R extends UseCase.ResponseValues> implements SynCommandHandler<T, R> {

    private static final Logger logger = Logger.getLogger(SyncCommandExecutor.class.getName());
    /**
     * The Handles.
     */
    protected Map<String, Consumer<Map<String, String>>> handles = new ConcurrentHashMap<>();


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
    public final R execute(String commandType, T args) {

        if (!Objects.nonNull(commandType)) {
            throw new IllegalArgumentException("The commandType of the aggregate must be specified");
        }

        if (!handles.containsKey(commandType)) {
            throw new ExecutionNoFound(commandType);
        }

        return executeCommand(args, commandType);
    }

    private R executeCommand(T args, String type) {
        logger.info("####### Executor Command #######");
        var consumer = handles.get(type);
        var useCaseExecutor = (UseCaseExecutor<T, R>) consumer;

        return useCaseExecutor.apply(args);
    }


}
