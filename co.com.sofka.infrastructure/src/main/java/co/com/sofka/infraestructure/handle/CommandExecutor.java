package co.com.sofka.infraestructure.handle;

import co.com.sofka.business.asyn.UseCaseExecutor;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.logging.Logger;


/**
 * The type Command executor.
 */
public abstract class CommandExecutor implements CommandHandler<Map<String, String>> {

    private static Logger logger = Logger.getLogger(CommandExecutor.class.getName());
    /**
     * The Handles.
     */
    protected Map<String, Consumer<Map<String, String>>> handles = new ConcurrentHashMap<>();

    /**
     * Put.
     *
     * @param consumer the consumer
     */
    protected void put(String type, Consumer<Map<String, String>> consumer) {
        handles.put(type, consumer);
    }

    @Override
    public final void execute(Map<String, String> args) {

        if(!args.containsKey("eventType")){
            throw new IllegalArgumentException("The eventType of the aggregate must be specified");
        }
        var type = args.get("eventType");

        if(!handles.containsKey(type)){
            throw new ExecutionNoFound(type);
        }

        executeCommand(args, type);
    }

    private void executeCommand(Map<String, String> args, String type) {
        logger.info("####### Executor Command #######");
        var consumer = handles.get(type);
        var useCaseExecutor = (UseCaseExecutor)consumer;

        if(args.containsKey("aggregateId")){
            useCaseExecutor.withAggregateId(args.get("aggregateId"));
        }

        consumer.accept(args);
    }
}
