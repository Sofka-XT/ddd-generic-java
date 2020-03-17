package co.com.sofka.infrastructure.model;

public class UserModel {
    private String name;

    public UserModel(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public UserModel() {
        super();
    }

    private String password;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
