package nl.tudelft.sem.template.reservations.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EquipmentReservationTest {
    private transient int id;
    private transient int id2;

    private transient int equipmentId;
    private transient int equipmentId2;

    private transient int reserver;
    private transient int reserver2;

    private transient LocalDateTime startTime;
    private transient LocalDateTime startTime2;

    public transient EquipmentReservation er1;
    public transient EquipmentReservation er2DiffId;
    public transient EquipmentReservation er3DiffEquip;
    public transient EquipmentReservation er4DiffReserver;
    public transient EquipmentReservation er5DiffStart;
    public transient EquipmentReservation er1Same;


    /**
     * Initialise method to call object before every test.
     */
    @BeforeEach
    public void beforeEach() {
        this.id = 1;
        this.id2 = 2;
        this.equipmentId = 42;
        this.equipmentId2 = 90;
        this.reserver = 89;
        this.reserver2 = 100;

        String dateTimeString = "2021-02-14T17:45:55";  //.9483536";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime1 = LocalDateTime.parse(dateTimeString, formatter);
        LocalDateTime dateTime2 = LocalDateTime.parse("2021-04-19T18:35:08", formatter);
        this.startTime = dateTime1;
        this.startTime2 = dateTime2;

        er1 = new EquipmentReservation(id, equipmentId, reserver, startTime);
        er2DiffId = new EquipmentReservation(id2, equipmentId, reserver, startTime);
        er3DiffEquip = new EquipmentReservation(id, equipmentId2, reserver, startTime);
        er4DiffReserver = new EquipmentReservation(id, equipmentId, reserver2, startTime);
        er5DiffStart = new EquipmentReservation(id, equipmentId, reserver, startTime2);
        er1Same = new EquipmentReservation(id, equipmentId, reserver, startTime);

    }

    @Test
    public void constructorTest() {
        Assertions.assertNotNull(er1);

    }

    @Test
    public void getSetIdTest() {
        er1.setId(7);
        assertEquals(7, er1.getId());
    }

    @Test
    public void getSetEquipmentNameTest() {
        er1.setEquipmentId(24);
        assertEquals(24, er1.getEquipmentId());
    }

    @Test
    public void getSetReserverTest() {
        er1.setReserver(42);
        assertEquals(42, er1.getReserver());
    }

    @Test
    public void getSetStartTimeTest() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse("2023-05-20T18:35:08", formatter);
        er1.setStartTime(dateTime);
        assertEquals(dateTime, er1.getStartTime());
    }

    @Test
    public void testSameObject() {
        Assertions.assertTrue(er1.equals(er1));
    }

    @Test
    public void testDiffId() {
        Assertions.assertFalse(er1.equals(er2DiffId));
    }

    @Test
    public void testDiffEquip() {
        Assertions.assertFalse(er1.equals(er3DiffEquip));
    }

    @Test
    public void testDiffReserver() {
        Assertions.assertFalse(er1.equals(er4DiffReserver));
    }

    @Test
    public void testDiffStart() {
        Assertions.assertFalse(er1.equals(er5DiffStart));
    }


    @Test
    public void testEqualsNull() {
        EquipmentReservation erNull = null;
        Assertions.assertFalse(er1.equals(erNull));
    }

    @Test
    public void testHash() {
        assertTrue(er1.hashCode() == er1Same.hashCode());
    }

    @Test
    public void testHashFalse() {
        Assertions.assertFalse(er2DiffId.hashCode() == er1Same.hashCode());
    }

}
