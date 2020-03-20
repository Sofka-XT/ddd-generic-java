package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;


/**
 * The interface Query handler.
 *
 * @param <Q> the type parameter
 */
@FunctionalInterface
public interface QueryHandler<Q extends Query> {
    /**
     * Search view model.
     *
     * @param query the query
     * @return the view model
     */
    ViewModel search(Q query);
}