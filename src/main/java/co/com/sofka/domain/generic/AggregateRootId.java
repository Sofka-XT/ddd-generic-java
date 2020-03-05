package co.com.sofka.domain.generic;


import java.util.Objects;
import java.util.UUID;

/**
 * The type Aggregate root id.
 *
 * @author Raul .A Alzate
 * @version 1.0
 * @since 2019 -03-01
 */
public class AggregateRootId implements ValueObject<String> {
    private final String uuid;

    /**
     * Instantiates a new Aggregate root id.
     *
     * @param uuid the uuid
     */
    public AggregateRootId(String uuid) {
        this.uuid = Objects.requireNonNull(uuid, "ID Aggregate can´t be null");
    }

    /**
     * Create aggregate root id.
     *
     * @return the aggregate root id
     */
    public static AggregateRootId create() {
        return new AggregateRootId(UUID.randomUUID().toString());
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AggregateRootId aggregateRootId = (AggregateRootId) o;
        return Objects.equals(uuid, aggregateRootId.uuid);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }


    /**
     * Value string.
     *
     * @return the string
     */
    @Override
    public String value() {
        return uuid;
    }
}
