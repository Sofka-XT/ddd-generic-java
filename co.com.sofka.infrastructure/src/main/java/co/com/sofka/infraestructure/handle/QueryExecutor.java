package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

/**
 * The type Query executor.
 */
public class QueryExecutor implements QueryHandler<Query> {
    /**
     * The Handles.
     */
    protected Set<Function<? super Query, ?>> handles = new HashSet<>();

    /**
     * Add.
     *
     * @param function the function
     */
    protected void add(Function<? extends Query, ?> function) {
        handles.add((Function<? super Query, ?>) function);
    }

    @Override
    public ViewModel get(Query query) {
        for (var consumer : handles) {
            try {
                return (ViewModel) consumer.apply(query);
            } catch (ClassCastException ignored) {
            }
        }
        throw new ExecutionNoFound("View Model by find one");
    }

    @Override
    public List<ViewModel> find(Query query) {
        for (var consumer : handles) {
            try {
                return (List<ViewModel>) consumer.apply(query);
            } catch (ClassCastException ignored) {
            }
        }
        throw new ExecutionNoFound("List view model");
    }
}
