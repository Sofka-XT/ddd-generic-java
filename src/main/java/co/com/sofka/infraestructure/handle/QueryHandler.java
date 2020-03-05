package co.com.sofka.infraestructure.handle;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.generic.Query;

/**
 * The interface Query handler.
 *
 * @param <Q> the type parameter
 * @param <R> the type parameter
 */
@FunctionalInterface
public interface QueryHandler<Q extends Query, R extends UseCase.ResponseValues> {
    /**
     * Handle r.
     *
     * @param query the query
     * @return the r
     */
    R handle(Q query);
}