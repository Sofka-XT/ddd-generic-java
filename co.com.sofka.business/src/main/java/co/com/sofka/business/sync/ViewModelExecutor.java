package co.com.sofka.business.sync;

import co.com.sofka.business.repository.QueryMapperRepository;
import co.com.sofka.business.repository.QueryRepository;
import co.com.sofka.domain.generic.ViewModel;

import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * The type View model executor.
 *
 * @param <T> the type parameter
 */
public abstract class ViewModelExecutor<T> implements Function<Map<String, String>, T> {

    private QueryMapperRepository queryMapperRepository;
    private QueryRepository<T> queryRepository;

    /**
     * Witch query mapper repository view model executor.
     *
     * @param queryMapperRepository the query mapper repository
     * @return the view model executor
     */
    public ViewModelExecutor<T> witchQueryMapperRepository(QueryMapperRepository queryMapperRepository) {
        this.queryMapperRepository = queryMapperRepository;
        return this;
    }

    /**
     * Witch query repository view model executor.
     *
     * @param queryRepository the query repository
     * @return the view model executor
     */
    public ViewModelExecutor<T> witchQueryRepository(QueryRepository<T> queryRepository) {
        this.queryRepository = queryRepository;
        return this;
    }

    /**
     * Query mapper repository query mapper repository.
     *
     * @return the query mapper repository
     */
    public QueryMapperRepository queryMapperRepository() {
        Objects.requireNonNull(queryMapperRepository, "The query mapper is not defined, consider using the witchQueryMapperRepository method");
        return queryMapperRepository;
    }

    /**
     * Query repository query repository.
     *
     * @return the query repository
     */
    public QueryRepository<T> queryRepository() {
        Objects.requireNonNull(queryRepository, "The query repository is not defined, consider using the witchQueryRepository method");
        return queryRepository;
    }

    /**
     * Gets data mapped.
     *
     * @param <M>            the type parameter
     * @param category       the category
     * @param classViewModel the class view model
     * @return the data mapped
     */
    public <M extends ViewModel> QueryMapperRepository.ApplyQuery<M> getDataMapped(String category, Class<M> classViewModel) {
        return queryMapperRepository()
                .getDataMapped(category, classViewModel);
    }
}
