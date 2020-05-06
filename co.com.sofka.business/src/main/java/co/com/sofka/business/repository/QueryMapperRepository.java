package co.com.sofka.business.repository;


import co.com.sofka.domain.generic.Query;
import co.com.sofka.domain.generic.ViewModel;

import java.util.List;

public interface QueryMapperRepository {
    ApplyQuery  getDataMapped(String collection, Class<?> classViewModel);
    interface ApplyQuery{
        <Q extends Query> List<ViewModel> applyAsList(Q query);
        <Q extends Query> ViewModel applyAsElement(Q query);
    }
}
