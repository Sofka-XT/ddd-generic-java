package co.com.sofka.domain.user.values;

import co.com.sofka.domain.generic.AggregateRootId;

import java.util.UUID;

public class UserId extends AggregateRootId {

    public UserId(String uuid) {
        super(uuid);
    }

    public static UserId create() {
        return new UserId(UUID.randomUUID().toString());
    }
}
