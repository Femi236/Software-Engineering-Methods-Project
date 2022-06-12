package nl.tudelft.sem.template.groups.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupUserIdTest {

    private transient GroupUserId groupUserId;

    private transient int group;

    private transient int user;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        this.group = 1;
        this.user = 2;

        this.groupUserId = new GroupUserId(group, user);
    }

    @Test
    public void getSetGroup() {
        groupUserId.setGroup(3);
        assertEquals(3, groupUserId.getGroup());
    }

    @Test
    public void getSetUser() {
        groupUserId.setUser(4);
        assertEquals(4, groupUserId.getUser());
    }
}
