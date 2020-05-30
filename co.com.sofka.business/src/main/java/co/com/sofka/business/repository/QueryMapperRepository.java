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
    <T extends ViewModel> ApplyQuery<T> getDataMapped(String category, Class<T> classViewModel);

    /**
     * The interface linked to Query mapper repository.
     * <p>
     * Query models of one view and multiple views.
     */
    interface ApplyQuery<T extends ViewModel> {
        /**
         * Apply as a list
         *
         * @param query the object query
         * @return the list view model
         */
        <Q extends Query> List<T> applyAsList(Q query);

        /**
         * Apply as a object
         *
         * @param query the object query
         * @return the object view model
         */
        <Q extends Query> T applyAsElement(Q query);
    }
}
