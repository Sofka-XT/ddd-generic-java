package co.com.sofka.domain.user.queries;

import co.com.sofka.domain.generic.Query;

public class FindById implements Query {
    private Long id;

    public FindById(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
