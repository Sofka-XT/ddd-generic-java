package co.com.sofka.domain.user.queries;

import co.com.sofka.domain.generic.Query;

public class FindById implements Query {
    private String id;

    public FindById(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
