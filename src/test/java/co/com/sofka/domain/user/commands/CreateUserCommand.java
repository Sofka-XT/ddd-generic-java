package co.com.sofka.domain.user.commands;

import co.com.sofka.domain.generic.Command;

public class CreateUserCommand extends Command {
    private String name;
    private String password;

    public CreateUserCommand() {
        super("user.create");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
