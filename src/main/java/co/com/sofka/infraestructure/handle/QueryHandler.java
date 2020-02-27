package co.com.sofka.infraestructure.handle;

import co.com.sofka.business.generic.UseCase;
import co.com.sofka.domain.generic.Query;

public interface QueryHandler<Q extends Query, R extends UseCase.ResponseValues> {
    R handle(Q query);
}