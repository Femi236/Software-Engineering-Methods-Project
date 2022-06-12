package nl.tudelft.sem.template.groups.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

@Embeddable
public class GroupUserId implements Serializable {
    public static final long serialVersionUID = 12346;

    private int group;
    private int user;

    public GroupUserId() {
    }

    public GroupUserId(int group, int user) {
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
