package co.com.sofka.business.repository;


import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;

import java.util.List;

/**
 * The interface Query mapper repository.
 * <p>
 * This interface must be implemented to map the views that can be as models and model list.
 */
public interface QueryMapperRepository {

    /**
     * Get data mapped.
     *
     * @param category       table and collection
     * @param classViewModel class to mapper
     * @return ApplyQuery mode list and object
     */
    ApplyQuery getDataMapped(String category, Class<?> classViewModel);

    /**
     * The interface linked to Query mapper repository.
     * <p>
     * Query models of one view and multiple views.
     */
    interface ApplyQuery {
        /**
         * Apply as a list
         *
         * @param query the object query
         * @return the list view model
         */
        <Q extends Query> List<ViewModel> applyAsList(Q query);

        /**
         * Apply as a object
         *
         * @param query the object query
         * @return the object view model
         */
        <Q extends Query> ViewModel applyAsElement(Q query);
    }
}
