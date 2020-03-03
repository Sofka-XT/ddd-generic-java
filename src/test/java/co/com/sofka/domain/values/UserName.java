package co.com.sofka.domain.values;

import co.com.sofka.domain.generic.ValueObject;

public class UserName implements ValueObject<String> {
    private final String name;

    public UserName(String name) {
        this.name = name;
    }

    @Override
    public String value() {
        return name;
    }
}
