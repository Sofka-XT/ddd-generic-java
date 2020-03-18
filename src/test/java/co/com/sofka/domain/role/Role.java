package co.com.sofka.domain.role;

import co.com.sofka.domain.generic.AggregateRoot;
import co.com.sofka.domain.user.User;

import java.util.HashSet;
import java.util.Set;

public class Role extends AggregateRoot<RoleId> {
    private RoleName roleName;
    private Set<User> users;
    public Role(RoleId entityId, RoleName roleName) {
        super(entityId);
        this.roleName = roleName;
        this.users = new HashSet<>();
    }


    public void assignUser(User user){
        users().add(user);
    }

    private RoleName roleName(){
        return roleName;
    }

    public Set<User> users(){
        return users;
    }

    public static Role from(RoleId entityId, RoleName roleName, Set<User> users){
        var role = new Role(entityId, roleName);
        role.users = users;
        return role;
    }

}
