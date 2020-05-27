package co.com.sofka.domain.generic;


import java.util.Objects;
import java.util.UUID;

/**
 * The type Identity.
 */
public class Identity implements ValueObject<String> {
    private final String uuid;

    /**
     * Instantiates a new Identity.
     *
     * @param uuid the uuid
     */
    public Identity(String uuid) {
        this.uuid = Objects.requireNonNull(uuid, "Identity can´t be null");
        if(this.uuid.isBlank()){
            throw new IllegalArgumentException("Identity can´t be blank");
        }
    }

    /**
     * Instantiates a new Identity.
     */
    public Identity() {
        this.uuid = this.generateUUID().toString();
    }

    /**
     * Generate uuid uuid.
     *
     * @return the uuid
     */
    public UUID generateUUID() {
        return UUID.randomUUID();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Identity identity = (Identity) object;
        return Objects.equals(uuid, identity.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }


    @Override
    public String value() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid;
    }
}
