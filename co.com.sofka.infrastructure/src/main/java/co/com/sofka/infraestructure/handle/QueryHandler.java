package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;

import java.util.List;


/**
 * The interface Query handler.
 *
 * @param <Q> the type parameter
 */
public interface QueryHandler<Q> {
    /**
     * Search view model.
     *
     * @param path the query
     * @param query object
     *
     * @return the view model
     */
    ViewModel get(String path, Q query);

    /**
     * List view model
     *
     * @param path the query
     * @param query object
     * @return the list view model
     */
    List<ViewModel> find(String path, Q query);
}