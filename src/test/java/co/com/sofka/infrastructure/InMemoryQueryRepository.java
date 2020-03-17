package co.com.sofka.infrastructure;

import co.com.sofka.domain.generic.Query;
import co.com.sofka.infraestructure.repository.QueryRepository;
import co.com.sofka.infrastructure.model.UserModel;

import java.util.Optional;
import java.util.stream.Stream;

public class InMemoryQueryRepository implements QueryRepository<UserModel> {
    @Override
    public Stream<UserModel> findAll() {
        return Stream.of(
                new UserModel("raul.alzate", "******"),
                new UserModel("andres.perez", "******"),
                new UserModel("federico.sanchez", "******"),
                new UserModel("juan.marin", "******")
        );
    }

    @Override
    public Optional<UserModel> get(Query query) {
        return Optional.of(new UserModel("raul.alzate", "******"));
    }
}
