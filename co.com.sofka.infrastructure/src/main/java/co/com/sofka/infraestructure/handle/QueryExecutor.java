package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;

/**
 * The type Query executor.
 */
public class QueryExecutor implements QueryHandler<Query> {
    /**
     * The Handles.
     */
    protected Set<Function<? super Query, ? super ViewModel>> handles = new HashSet<>();

    /**
     * Add.
     *
     * @param function the function
     */
    protected void add(Function<? extends Query, ? extends ViewModel> function) {
        handles.add((Function<? super Query, ? super ViewModel>) function);
    }

    @Override
    public ViewModel search(Query query) {
        for (var consumer : handles) {
            try {
                return (ViewModel) consumer.apply(query);
            } catch (ClassCastException ignored) {
            }
        }
        throw new ExecutionNoFound(query);
    }
}
