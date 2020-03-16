package co.com.sofka.infrastructure.view;

import co.com.sofka.domain.generic.ViewModel;
import co.com.sofka.infrastructure.model.UserModel;

import java.util.List;

public class ListUserView implements ViewModel {
    List<UserModel> userList;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    Integer page;

    public List<UserModel> getUserList() {
        return userList;
    }

    public void setUserList(List<UserModel> userList) {
        this.userList = userList;
    }
}
