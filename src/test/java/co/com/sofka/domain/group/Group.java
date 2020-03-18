package co.com.sofka.domain.group;

import co.com.sofka.domain.generic.Entity;

public class Group extends Entity<GroupId> {

    public Group(GroupId entityId) {
        super(entityId);
    }

    public void changeName(GroupName groupName){
        //....
    }
}
