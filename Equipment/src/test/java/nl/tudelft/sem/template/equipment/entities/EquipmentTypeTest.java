package nl.tudelft.sem.template.equipment.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipmentTypeTest {
    private transient int id;
    private transient String name1;
    private transient EquipmentType equipmentType;


    /** .
     *Initialise method to call object before every test
     */
    @BeforeEach
    public void initialize() {
        this.id  = 1;
        this.name1 = "basketball";
        this.equipmentType = new EquipmentType(id, name1);
    }


    @Test
    public void constructorTest() {
        assertNotNull(equipmentType);

    }

    @Test
    public void getSetIdTest() {
        equipmentType.setId(5);
        assertEquals(5, equipmentType.getId());

    }

    @Test
    public void getSetNameTest() {
        equipmentType.setName("football");
        assertEquals("football", equipmentType.getName());
    }


}
