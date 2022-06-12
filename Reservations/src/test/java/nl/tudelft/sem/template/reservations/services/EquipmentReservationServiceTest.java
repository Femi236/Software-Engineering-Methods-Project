package nl.tudelft.sem.template.reservations.services;



import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.repositories.EquipmentReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

@ExtendWith(MockitoExtension.class)
public class EquipmentReservationServiceTest {
    private transient EquipmentReservation er1;
    private transient EquipmentReservation er2;
    private transient EquipmentReservation er3;
    private transient EquipmentReservation er4;
    private transient List<EquipmentReservation> list = new ArrayList<>();
    private transient String nullParameterError = "Parameters cannot be null";

    @Mock
    private transient EquipmentReservationRepository equipResRep;

    @InjectMocks
    private transient EquipmentReservationService equipResSer;


    /**initialiser.
     *
     */
    @BeforeEach
    public void beforeEach() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime time1 = LocalDateTime.parse("2030-12-12T12:12:12", formatter);
        LocalDateTime time2 = LocalDateTime.parse("2021-01-01T14:18:52", formatter);
        LocalDateTime time3 = LocalDateTime.parse("2024-04-09T09:30:30", formatter);
        er1 = new EquipmentReservation(1, 35, 89, time1);
        er2 = new EquipmentReservation(2, 35, 9, time2);
        er3 = new EquipmentReservation(3, 20, 17, time3);
        er4 = new EquipmentReservation(4, 100, 17, time1);
        list.add(er1);
        list.add(er2);
        list.add(er3);
        list.add(er4);
    }

    @Test //yes
    public void correctlySaveEquipRes() {
        when(equipResRep.save(any(EquipmentReservation.class)))
                .then(returnsFirstArg()); //what is firstArg??
        String response = equipResSer
                .addNewEquipmentRes(er1.getId(), er1.getEquipmentId(),
                        er1.getReserver(), er1.getStartTime());
        assertEquals("Saved", response);
    }

    @Test //yes
    public void correctlyDeleteEquipRes() {
        doNothing().when(equipResRep).deleteById(any(Integer.class));
        String response = equipResSer.deleteEquipmentRes(er1.getId());
        assertEquals("Deleted EquipmentReservation", response);
    }

    @Test
    public void nullId() {
        er1.setId(null);
        String response = equipResSer.addNewEquipmentRes(er1.getId(), er1.getEquipmentId(),
                er1.getReserver(), er1.getStartTime());
        assertEquals("Id was null",
                response);
    }

    @Test
    public void nullEquipmentId() {
        er1.setEquipmentId(null);
        String response = equipResSer.addNewEquipmentRes(er1.getId(), er1.getEquipmentId(),
                er1.getReserver(), er1.getStartTime());
        assertEquals(nullParameterError,
                response);
    }

    @Test
    public void nullReserver() {
        er1.setReserver(null);
        String response = equipResSer.addNewEquipmentRes(er1.getId(), er1.getEquipmentId(),
                er1.getReserver(), er1.getStartTime());
        assertEquals(nullParameterError,
                response);
    }

    @Test
    public void nullStartTime() {
        er1.setStartTime(null);
        String response = equipResSer.addNewEquipmentRes(er1.getId(), er1.getEquipmentId(),
                er1.getReserver(), er1.getStartTime());
        assertEquals(nullParameterError,
                response);
    }


    @Test
    public void correctlyUpdatesEquipRes() {
        when(equipResRep.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(er1));
        when(equipResRep.save((any(EquipmentReservation.class))))
                .then(returnsFirstArg());
        String response = equipResSer.updateEquipmentReservation(
                er1.getId(), er1.getEquipmentId(),
                er1.getReserver(), er1.getStartTime());
        assertEquals("updated", response);
    }

    @Test
    public void updateNonExistingEquipRes() {
        when(equipResRep.findById(not(eq(2))))
                .thenReturn(Optional.ofNullable(null));
        String response = equipResSer
                .updateEquipmentReservation(er1.getId(), er1.getEquipmentId(),
                        er1.getReserver(), er1.getStartTime());
        assertEquals("EquipmentReservation does not exist", response);
    }

    @Test
    public void updatesWithEquipmentNull() {
        when(equipResRep.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(er1));

        er1.setEquipmentId(null);
        String response = equipResSer.updateEquipmentReservation(
                er1.getId(), er1.getEquipmentId(),
                er1.getReserver(), er1.getStartTime());
        assertEquals(nullParameterError, response);
    }

    @Test
    public void updatesReserverNull() {
        when(equipResRep.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(er1));
        er1.setReserver(null);
        String response = equipResSer.updateEquipmentReservation(
                er1.getId(), er1.getEquipmentId(),
                er1.getReserver(), er1.getStartTime());
        assertEquals(nullParameterError, response);
    }

    @Test
    public void updatesStartNull() {
        when(equipResRep.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(er1));
        er1.setStartTime(null);
        String response = equipResSer.updateEquipmentReservation(
                er1.getId(), er1.getEquipmentId(),
                er1.getReserver(), er1.getStartTime());
        assertEquals(nullParameterError, response);
    }


    @Test
    public void correctlyReturnAllEquipRes() {
        when(equipResRep.findAll())
                .thenReturn(list);
        ArrayList<EquipmentReservation> expected = new ArrayList<>();
        expected.add(er1);
        expected.add(er2);
        expected.add(er3);
        expected.add(er4);
        Iterable<EquipmentReservation> response = equipResSer.getAllEquipmentRes();
        assertEquals(expected, response);
    }

    @Test
    public void deleteNonExistingEquipRes() {
        doThrow(new EmptyResultDataAccessException(1)). //1 refers to the size
                when(equipResRep).deleteById(any(Integer.class));
        String response = equipResSer.deleteEquipmentRes(er1.getId());
        assertEquals("FAILED, EquipmentReservation does not exist", response);
    }

    @Test
    public void correctlyReturnUserEquipRes() {
        when(equipResRep.findAll())
                .thenReturn(list);
        ArrayList<EquipmentReservation> expected = new ArrayList<>();
        expected.add(er3);
        expected.add(er4);
        Iterable<EquipmentReservation> response = equipResSer.userReservations(17);
        assertEquals(expected, response);
    }

    @Test
    public void correctlyReturnEquipResOfEquip() {
        when(equipResRep.findAll())
                .thenReturn(list);
        ArrayList<EquipmentReservation> expected = new ArrayList<>();
        expected.add(er1);
        expected.add(er2);
        Iterable<EquipmentReservation> response = equipResSer.getReservationsForEquipment(35);
        assertEquals(expected, response);
    }

}


