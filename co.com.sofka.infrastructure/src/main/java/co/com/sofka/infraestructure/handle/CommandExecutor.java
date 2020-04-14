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
        logger.info("####### Executor Command #######");
        var type = args.getOrDefault("type", null);
        if(!handles.containsKey(type)){
            throw new ExecutionNoFound(type);
        }
        var consumer = handles.get(type);
        if(args.containsKey("aggregateId")){
            ((UseCaseExecutor)consumer).withAggregateId(args.get("aggregateId"));
        }
        if(args.containsKey("aggregateRootId")){
            ((UseCaseExecutor)consumer).withAggregateId(args.get("aggregateRootId"));
        }
        consumer.accept(args);
    }
}
