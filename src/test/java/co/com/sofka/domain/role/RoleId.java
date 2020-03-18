package co.com.sofka.domain.role;

import co.com.sofka.domain.generic.AggregateRootId;

import java.util.UUID;

public class RoleId extends AggregateRootId {
    public RoleId(String uuid) {
        super(uuid);
    }
    public static RoleId create() {
        return new RoleId(UUID.randomUUID().toString());
    }

}
