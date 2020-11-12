package co.com.sofka.infraestructure.handle;

import co.com.sofka.business.asyn.UseCaseExecutor;
import co.com.sofka.business.generic.UseCase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Logger;


/**
 * The type Command executor.
 */
public abstract class AsyncCommandExecutor implements AsyncCommandHandler<Map<String, String>> {

    private static final Logger logger = Logger.getLogger(AsyncCommandExecutor.class.getName());
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

        if (!args.containsKey("commandType")) {
            throw new IllegalArgumentException("The commandType of the aggregate must be specified");
        }
        var type = args.get("commandType");

        if (!handles.containsKey(type)) {
            throw new ExecutionNoFound(type);
        }

        executeCommand(args, type);
    }

    private void executeCommand(Map<String, String> args, String type) {
        logger.info("####### Executor Command #######");
        var consumer = handles.get(type);
        var useCaseExecutor = (UseCaseExecutor) consumer;

        if (args.containsKey("aggregateId")) {
            useCaseExecutor.withAggregateId(args.get("aggregateId"));
        }

        useCaseExecutor.executor(args);
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
