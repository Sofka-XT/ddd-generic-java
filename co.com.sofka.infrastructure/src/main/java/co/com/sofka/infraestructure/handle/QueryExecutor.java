package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;

/**
 * The type Query executor.
 */
public class QueryExecutor implements QueryHandler<Map<String, String>> {
    private static Logger logger = Logger.getLogger(QueryExecutor.class.getName());

    /**
     * The Handles.
     */
    protected Set<Function<Map<String, String>, ?>> handles = new HashSet<>();

    /**
     * Add.
     *
     * @param function the function
     */
    protected void add(Function<Map<String, String>, ?> function) {
        handles.add(function);
    }

    @Override
    public ViewModel get(Map<String, String> query) {
        for (var consumer : handles) {
            try {
                var apply = (ViewModel) consumer.apply(query);
                logger.info("Query applied OK --> "+query);
                return apply;
            } catch (ClassCastException ignored) {
            }
        }
        throw new ExecutionNoFound("View Model by find one");
    }

    @Override
    public List<ViewModel> find(Map<String, String> query) {
        for (var consumer : handles) {
            try {
                var apply =  (List<ViewModel>) consumer.apply(query);
                logger.info("Query applied OK --> "+query);
                return apply;
            } catch (ClassCastException ignored) {
            }
        }
        throw new ExecutionNoFound("List view model");
    }
}
