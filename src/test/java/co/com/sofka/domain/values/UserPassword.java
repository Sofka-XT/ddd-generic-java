package co.com.sofka.domain.values;

import co.com.sofka.domain.generic.ValueObject;

public class UserPassword implements ValueObject<String> {
    private final String newPassword;
    public UserPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String value() {
        return newPassword;
    }
}
