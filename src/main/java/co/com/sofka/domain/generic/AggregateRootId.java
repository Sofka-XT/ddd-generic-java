package co.com.sofka.domain.generic;


import java.util.Objects;
import java.util.UUID;

public class AggregateRootId {
    private final String uuid;

    public AggregateRootId(String uuid) {
        this.uuid = Objects.requireNonNull(uuid, "ID Aggregate can´t be null");
    }

    public static AggregateRootId create(){
        return new AggregateRootId(UUID.randomUUID().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateRootId aggregateRootId = (AggregateRootId) o;
        return Objects.equals(uuid, aggregateRootId.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return uuid;
    }
}
