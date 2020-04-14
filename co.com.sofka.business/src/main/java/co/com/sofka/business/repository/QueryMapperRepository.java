package co.com.sofka.business.repository;


import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;

import java.util.List;

public interface QueryMapperRepository<T> {
    <Q extends Query> T getDataMapped(String col, Q query, Class<?> classViewModel);
}
