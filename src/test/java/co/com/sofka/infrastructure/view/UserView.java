package co.com.sofka.infrastructure.view;

import co.com.sofka.domain.generic.ViewModel;
import co.com.sofka.infrastructure.model.UserModel;

public class UserView implements ViewModel {
    private UserModel user;

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
