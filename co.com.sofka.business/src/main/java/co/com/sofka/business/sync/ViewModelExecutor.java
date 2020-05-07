package co.com.sofka.business.sync;

import co.com.sofka.business.repository.QueryMapperRepository;
import co.com.sofka.business.repository.QueryRepository;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Query executor
 * <p>
 * This class allows you to run the view models for the queries and the query handler
 *
 * @param <T>
 */
public abstract class ViewModelExecutor<T> implements Function<Map<String, String>, T> {

    private QueryMapperRepository queryMapperRepository;
    private QueryRepository<T> queryRepository;

    /**
     * Witch query mapper repository
     *
     * @param queryMapperRepository the query mapper repository
     */
    public ViewModelExecutor<T> witchQueryMapperRepository(QueryMapperRepository queryMapperRepository) {
        this.queryMapperRepository = queryMapperRepository;
        return this;
    }

    /**
     * Witch query repository
     *
     * @param queryRepository the query repository
     */
    public ViewModelExecutor<T> witchQueryRepository(QueryRepository<T> queryRepository) {
        this.queryRepository = queryRepository;
        return this;
    }

    /**
     * query mapper repository
     *
     * @return the query mapper repository
     */
    public QueryMapperRepository queryMapperRepository() {
        Objects.requireNonNull(queryMapperRepository, "The query mapper is not defined, consider using the witchQueryMapperRepository method");
        return queryMapperRepository;
    }

    /**
     * Query repository
     *
     * @return the query repository
     */
    public QueryRepository<T> queryRepository() {
        Objects.requireNonNull(queryRepository, "The query repository is not defined, consider using the witchQueryRepository method");
        return queryRepository;
    }

    /**
     * Get data mapped
     *
     * @param category       table and collection
     * @param classViewModel class to mapper
     * @return ApplyQuery to list or model object
     */
    public QueryMapperRepository.ApplyQuery getDataMapped(String category, Class<?> classViewModel) {
        return queryMapperRepository()
                .getDataMapped(category, classViewModel);
    }
}
