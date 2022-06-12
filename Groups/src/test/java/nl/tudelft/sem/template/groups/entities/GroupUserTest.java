package nl.tudelft.sem.template.groups.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupUserTest {

    private transient int group;
    private transient int user;

    private transient GroupUser groupUser;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        this.group = 1;
        this.user = 2;
        this.groupUser = new GroupUser(group, user);
    }

    @Test
    public void constructorTest() {
        assertNotNull(groupUser);
    }

    @Test
    public void emptyConstructorTest() {
        GroupUser groupUser1 = new GroupUser();
        assertNotNull(groupUser1);
    }

    @Test
    public void getSetGroupTest() {
        groupUser.setGroup(2);
        assertEquals(2, groupUser.getGroup());
    }

    @Test
    public void getSetUserTest() {
        groupUser.setUser(4);
        assertEquals(4, groupUser.getUser());
    }

}
