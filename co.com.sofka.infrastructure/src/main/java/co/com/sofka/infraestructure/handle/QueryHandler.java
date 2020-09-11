package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.ViewModel;

import java.util.List;


/**
 * The interface Query handler.
 *
 * @param <Q> the type parameter
 */
public interface QueryHandler<Q> {
    /**
     * Get view model.
     *
     * @param path  the path
     * @param query the query
     * @return the view model
     */
    ViewModel get(String path, Q query);

    /**
     * Find list.
     *
     * @param path  the path
     * @param query the query
     * @return the list
     */
    List<ViewModel> find(String path, Q query);
}