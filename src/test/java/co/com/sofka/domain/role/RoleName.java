package co.com.sofka.domain.role;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class RoleName implements ValueObject<String> {
    private final String name;

    public RoleName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    @Override
    public String value() {
        return name;
    }
}
