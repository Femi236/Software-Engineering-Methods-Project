package nl.tudelft.sem.template.groups.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "team_user")
@IdClass(GroupUserId.class)
public class GroupUser {


    @Id
    @Column(name = "team")
    private int group;

    @Id
    private int user;

    public GroupUser() {

    }

    public GroupUser(int group, int user) {
        this.group = group;
        this.user = user;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }
}