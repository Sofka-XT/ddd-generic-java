package co.com.sofka.business.repository;


import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;

import java.util.List;

/**
 * The interface Query mapper repository.
 */
public interface QueryMapperRepository {

    /**
     * Gets data mapped.
     *
     * @param <T>            the type parameter
     * @param category       the category
     * @param classViewModel the class view model
     * @return the data mapped
     */
    <T extends ViewModel> ApplyQuery<T> getDataMapped(String category, Class<T> classViewModel);

    /**
     * The interface Apply query.
     *
     * @param <T> the type parameter
     */
    interface ApplyQuery<T extends ViewModel> {
        /**
         * Apply as list list.
         *
         * @param <Q>   the type parameter
         * @param query the query
         * @return the list
         */
        <Q extends Query> List<T> applyAsList(Q query);

        /**
         * Apply as element t.
         *
         * @param <Q>   the type parameter
         * @param query the query
         * @return the t
         */
        <Q extends Query> T applyAsElement(Q query);
    }
}
