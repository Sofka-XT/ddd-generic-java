package co.com.sofka.business.sync;

import co.com.sofka.business.repository.QueryMapperRepository;
import co.com.sofka.business.repository.QueryRepository;

import java.util.Map;
import java.util.function.Function;

/**
 * Query executor
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
    public ViewModelExecutor<T> witchQueryMapperRepository(QueryMapperRepository queryMapperRepository){
        this.queryMapperRepository = queryMapperRepository;
        return this;
    }

    /**
     * Witch query repository
     *
     * @param queryRepository the query repository
     */
    public ViewModelExecutor<T> witchQueryRepository(QueryRepository<T> queryRepository){
        this.queryRepository = queryRepository;
        return this;
    }

    /**
     * query mapper repository
     *
     * @return the query mapper repository
     */
    public QueryMapperRepository queryMapperRepository() {
        return queryMapperRepository;
    }

    /**
     * Query repository
     *
     * @return the query repository
     */
    public QueryRepository<T> queryRepository() {
        return queryRepository;
    }

}
