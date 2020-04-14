package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;

import java.util.List;


/**
 * The interface Query handler.
 *
 * @param <Q> the type parameter
 */
public interface QueryHandler<Q extends Query> {
    /**
     * Search view model.
     *
     * @param query the query
     * @return the view model
     */
    ViewModel get(Q query);

    /**
     * List view model
     *
     * @param query the query
     * @return the list view model
     */
    List<ViewModel> find(Q query);
}