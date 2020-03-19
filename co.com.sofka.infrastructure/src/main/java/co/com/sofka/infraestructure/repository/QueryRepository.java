package co.com.sofka.infraestructure.repository;

import co.com.sofka.domain.generic.Query;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * The interface Query repository.
 *
 * @param <E> the type parameter
 */
public interface QueryRepository<E> {
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

    /**
     * Find all stream.
     *
     * @return the stream
     */
    default Stream<E> findAll() {
        return Stream.empty();
    }

    /**
     * Find all stream.
     *
     * @param sort the sort
     * @return the stream
     */
    default Stream<E> findAll(Sort sort) {
        return Stream.empty();
    }

    /**
     * Find stream.
     *
     * @param query the query
     * @return the stream
     */
    default Stream<E> find(Query query) {
        return Stream.empty();
    }

    /**
     * Find stream.
     *
     * @param query the query
     * @param sort  the sort
     * @return the stream
     */
    default Stream<E> find(Query query, Sort sort) {
        return Stream.empty();
    }

    /**
     * Get optional.
     *
     * @param query the query
     * @return the optional
     */
    default Optional<E> get(Query query) {
        return Optional.empty();
    }
}
