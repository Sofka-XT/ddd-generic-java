package co.com.sofka.infraestructure.handle;

import co.com.sofka.domain.generic.ViewModel;
import co.com.sofka.domain.generic.Query;


@FunctionalInterface
public interface QueryHandler<Q extends Query> {
    ViewModel search(Q query);
}