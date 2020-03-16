package co.com.sofka.domain.user.commands;

import co.com.sofka.domain.generic.Command;

public class UpdateUsernameCommand extends Command {
    private String newUsername;
    public UpdateUsernameCommand() {
        super("user.update.username");
    }
    public String getNewUsername() {
        return newUsername;
    }

    public void setNewUsername(String newUsername) {
        this.newUsername = newUsername;
    }


}
