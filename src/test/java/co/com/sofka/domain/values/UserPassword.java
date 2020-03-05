package co.com.sofka.domain.values;

import co.com.sofka.domain.generic.ValueObject;

import java.util.Objects;

public class UserPassword implements ValueObject<String> {
    private final String newPassword;

    public UserPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String value() {
        return newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPassword that = (UserPassword) o;
        return Objects.equals(newPassword, that.newPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(newPassword);
    }
}
