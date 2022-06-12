package nl.tudelft.sem.template.equipment.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipmentTest {
    private transient int id;

    private transient int id2;

    private transient int type;

    private transient Equipment equipment;

    private transient Equipment equipment2;




    /** .
     *Initialise method to call object before every test
     */
    @BeforeEach
    public void initialize() {
        this.id  = 1;
        this.type = 3;
        this.id2 = 2;
        this.equipment = new Equipment(id, type);
        this.equipment = new Equipment(id2, type);
    }


    @Test
    public void constructorTest() {
        assertNotNull(equipment);

    }

    @Test
    public void getSetIdTest() {
        equipment.setId(5);
        assertEquals(5, equipment.getId());

    }

    @Test
    public void getSetType() {
        equipment.setType(8);
        assertEquals(8, equipment.getType());
    }
}
