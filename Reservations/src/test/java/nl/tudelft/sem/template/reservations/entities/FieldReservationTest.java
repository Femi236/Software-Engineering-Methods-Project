package nl.tudelft.sem.template.reservations.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FieldReservationTest {
    private transient int id;
    private transient int id2;

    private transient int fieldId;
    private transient int fieldId2;

    private transient int reserver;
    private transient int reserver2;

    private transient LocalDateTime startTime;
    private transient LocalDateTime startTime2;

    private transient int group;
    private transient int group2;

    public transient FieldReservation fr1;
    public transient FieldReservation fr2DiffId;
    public transient FieldReservation fr3DiffField;
    public transient FieldReservation fr4DiffReserver;
    public transient FieldReservation fr5DiffStart;
    public transient FieldReservation fr6DiffGroup;
    public transient FieldReservation fr1Same;


    /**
     * Initialise method to call object before every test.
     */
    @BeforeEach
    public void beforeEach() {
        this.id = 1;
        this.id2 = 2;
        this.fieldId = 42;
        this.fieldId2 = 90;
        this.reserver = 89;
        this.reserver2 = 100;
        this.group = 1;
        this.group2 = -1;

        String dateTimeString = "2021-02-14T17:45:55";  //.9483536";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime1 = LocalDateTime.parse(dateTimeString, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse("2021-04-19T18:35:08", formatter);
        this.startTime = dateTime1;
        this.startTime2 = dateTime2;

        fr1 = new FieldReservation(id, fieldId, reserver, startTime, group);
        fr2DiffId = new FieldReservation(id2, fieldId, reserver, startTime, group);
        fr3DiffField = new FieldReservation(id, fieldId2, reserver, startTime, group);
        fr4DiffReserver = new FieldReservation(id, fieldId, reserver2, startTime, group);
        fr5DiffStart = new FieldReservation(id, fieldId, reserver, startTime2, group);
        fr6DiffGroup = new FieldReservation(id, fieldId, reserver, startTime, group2);
        fr1Same = new FieldReservation(id, fieldId, reserver, startTime, group);
    }

    @Test
    public void constructorTest() {
        Assertions.assertNotNull(fr1);

    }

    @Test
    public void getSetIdTest() {
        fr1.setId(7);
        assertEquals(7, fr1.getId());
    }

    @Test
    public void getSetFieldTest() {
        fr1.setFieldId(24);
        assertEquals(24, fr1.getFieldId());
    }

    @Test
    public void getSetReserverTest() {
        fr1.setReserver(42);
        assertEquals(42, fr1.getReserver());
    }

    @Test
    public void getSetStartTimeTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse("2023-05-20T18:35:08", formatter);
        fr1.setStartTime(dateTime);
        assertEquals(dateTime, fr1.getStartTime());
    }

    @Test
    public void testSameObject() {
        Assertions.assertTrue(fr1.equals(fr1));
    }

    @Test
    public void testDiffId() {
        assertFalse(fr1.equals(fr2DiffId));
    }

    @Test
    public void testDiffField() {
        assertFalse(fr1.equals(fr3DiffField));
    }

    @Test
    public void testDiffReserver() {
        assertFalse(fr1.equals(fr4DiffReserver));
    }

    @Test
    public void testDiffStart() {
        assertFalse(fr1.equals(fr5DiffStart));
    }

    @Test
    public void testDiffGroup() {
        assertFalse(fr1.equals(fr6DiffGroup));
    }


    @Test
    public void testNullEquals() {
        FieldReservation frNull = null;
        assertFalse(fr1.equals(frNull));
    }


    @Test
    public void testHash() {
        assertTrue(fr1.hashCode() == fr1Same.hashCode());
    }

    @Test
    public void testHashFalse() {
        assertFalse(fr1.hashCode() == fr2DiffId.hashCode());
    }

}
