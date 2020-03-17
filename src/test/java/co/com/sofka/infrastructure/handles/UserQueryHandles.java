package co.com.sofka.infrastructure.handles;

import co.com.sofka.domain.user.queries.All;
import co.com.sofka.domain.user.queries.FindById;
import co.com.sofka.infraestructure.annotation.QueryHandles;
import co.com.sofka.infraestructure.handle.QueryExecutor;
import co.com.sofka.infraestructure.repository.QueryRepository;
import co.com.sofka.infrastructure.model.UserModel;
import co.com.sofka.infrastructure.view.ListUserView;
import co.com.sofka.infrastructure.view.UserView;

import java.util.function.Function;
import java.util.stream.Collectors;

@QueryHandles
public class UserQueryHandles extends QueryExecutor {
    private QueryRepository<UserModel> repository;

    public UserQueryHandles(QueryRepository<UserModel> repository) {
        this.repository = repository;
    }

    {
        add(new Function<All, ListUserView>() {
            @Override
            public ListUserView apply(All query) {
                var view = new ListUserView();
                view.setPage(1);
                view.setUserList(repository.findAll().collect(Collectors.toList()));
                return view;
            }
        });

        add(new Function<FindById, UserView>() {
            @Override
            public UserView apply(FindById query) {
                var user = repository.get(query).orElse(new UserModel());
                var view = new UserView();
                view.setUser(user);
                return view;
            }
        });
    }
}
