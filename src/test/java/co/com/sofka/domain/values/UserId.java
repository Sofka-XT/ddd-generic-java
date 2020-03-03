package co.com.sofka.domain.values;

import co.com.sofka.domain.generic.AggregateRootId;

public class UserId extends AggregateRootId  {

    public UserId(String uuid) {
        super(uuid);
    }
}
