package nl.tudelft.sem.template.field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class FieldTest {

    private transient int id1;

    private transient int id2;

    private transient String name1;

    private transient String name2;

    private transient int minCapacity;

    private transient int maxCapacity1;

    private transient int maxCapacity2;

    private transient Field field1;

    private transient Field field2;


    /** .
     *Initialise method to call object before every test
     */
    @BeforeEach
    public void initialize() {
        this.id1 = 1;
        this.id2 = 2;
        this.name1 = "Tennis court";
        this.name2  = "Football field";
        this.minCapacity = 2;
        this.maxCapacity1 = 4;
        this.maxCapacity2 = 16;

        this.field1 = new Field(id1, name1, minCapacity, maxCapacity1);
        this.field2 = new Field(id2, name2, minCapacity, maxCapacity2);

    }

    @Test
    public void constructorTest() {
        assertNotNull(field1);
    }

    @Test
    public void setIdTest() {
        field1.setId(10);
        assertEquals(10, field1.getId());
    }

    @Test
    public void setNameTest() {
        field1.setName("Dance hall");
        assertEquals("Dance hall", field1.getName());
    }

    @Test
    public void setMinCapacityTest() {
        field1.setMin_capacity(6);
        assertEquals(6, field1.getMin_capacity());
    }

    @Test
    public void setMaxCapacityTest() {
        field1.setMax_capacity(20);
        assertEquals(20, field1.getMax_capacity());
    }

    @Test
    public void comparingObjectsTest() {
        assertFalse(field1.equals(field2));
    }

    @Test
    public void comparingSameInstancestest() {
        assertTrue(field1.equals(field1));
    }



    @Test
    public void differentObjectTest() {
        String dateTimeString = "2021-02-14T17:45:55";  //.9483536";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime1 = LocalDateTime.parse(dateTimeString, formatter);
        assertFalse(field1.equals(dateTime1));
    }

    @Test
    public void equalsIdTest() {
        Field field3 = new Field(6, name1, 2, 4);
        assertFalse(field1.equals(field3));
    }

    @Test
    public void equalsNameTest() {
        Field field3 = new Field(1, "Basketball court", 2, 4);
        assertFalse(field1.equals(field3));
    }

    @Test
    public void equalsMinCapacityTest() {
        Field field3 = new Field(1, name1, 3, 4);
        assertFalse(field1.equals(field3));

    }

    @Test
    public void equalsMaxCapacityTest() {
        Field field3 = new Field(1, name1, 2, 5);
        assertFalse(field1.equals(field3));
    }

    @Test
    public void equalsNullTest() {
        Field fieldNull = null;
        assertFalse(field1.equals(fieldNull));
    }

    @Test
    public void hashCodeTest() {
        assertFalse(field1.hashCode() == 0);
    }







}
