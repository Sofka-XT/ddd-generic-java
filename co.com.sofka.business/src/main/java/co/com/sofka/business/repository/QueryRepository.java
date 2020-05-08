package co.com.sofka.business.repository;

import co.com.sofka.domain.generic.Query;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * The interface Query repository.
 */
public interface QueryRepository<T> {
    /**
     * Find all stream.
     *
     * @return the stream
     */
    default Stream<T> findAll() {
        return Stream.empty();
    }

    /**
     * Find all stream.
     *
     * @param sort the sort
     * @return the stream
     */
    default Stream<T> findAll(Sort sort) {
        return Stream.empty();
    }

    /**
     * Find stream.
     *
     * @param query the query
     * @return the stream
     */
    default Stream<T> find(Query query) {
        return Stream.empty();
    }

    /**
     * Find stream.
     *
     * @param query the query
     * @param sort  the sort
     * @return the stream
     */
    default Stream<T> find(Query query, Sort sort) {
        return Stream.empty();
    }

    /**
     * Get optional.
     *
     * @param query the query
     * @return the optional
     */
    default Optional<T> get(Query query) {
        return Optional.empty();
    }

    /**
     * The enum Sort.
     */
    enum Sort {
        /**
         * Asc sort.
         */
        ASC,
        /**
         * Desc sort.
         */
        DESC
    }
}
