package nl.tudelft.sem.template.groups.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GroupTest {

    private transient int id;

    private transient int creator;

    private transient Group group;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        this.id = 1;
        this.creator = 2;
        this.group = new Group(1, 2);
    }

    @Test
    public void constructorTest() {
        assertNotNull(group);
    }

    @Test
    public void emptyConstructorTest() {
        Group emptyGroup = new Group();
        assertNotNull(emptyGroup);
    }

    @Test
    public void getSetIdTest() {
        group.setId(3);
        assertEquals(3, group.getId());
    }

    @Test
    public void getSetCreator() {
        group.setCreator(3);
        assertEquals(3, group.getCreator());
    }



}
