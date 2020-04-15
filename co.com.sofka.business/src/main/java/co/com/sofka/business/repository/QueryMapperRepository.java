package co.com.sofka.business.repository;


import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;

import java.util.List;

public interface QueryMapperRepository<T> {
    ApplyQuery<T>  getDataMapped(String collection, Class<?> classViewModel);
    interface ApplyQuery<T>{
        <Q extends Query> T applyAsList(Q query);
        <Q extends Query> T applyAsElement(Q query);
    }
}
