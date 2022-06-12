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
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.repositories.FieldReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;


@ExtendWith(MockitoExtension.class)
public class FieldReservationServiceTest {
    private transient FieldReservation fr1;
    private transient FieldReservation fr2;
    private transient FieldReservation fr3;
    private transient FieldReservation fr4;
    private transient List<FieldReservation> list = new ArrayList<>();
    private transient String nullParameterError = "Parameters cannot be null";

    @Mock
    private transient FieldReservationRepository fieldResRep;

    @InjectMocks
    private transient FieldReservationService fieldResSer;


    /**initialiser.
     *
     */
    @BeforeEach
    public void beforeEach() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime time1 = LocalDateTime.parse("2030-12-12T12:12:12", formatter);
        LocalDateTime time2 = LocalDateTime.parse("2021-01-01T14:18:52", formatter);
        LocalDateTime time3 = LocalDateTime.parse("2024-04-09T09:30:30", formatter);
        fr1 = new FieldReservation(1, 35, 89, time1, 11);
        fr2 = new FieldReservation(2, 35, 9, time2, 12);
        fr3 = new FieldReservation(3, 20, 17, time3, 13);
        fr4 = new FieldReservation(4, 100, 17, time1, 14);
        list.add(fr1);
        list.add(fr2);
        list.add(fr3);
        list.add(fr4);
    }

    @Test //yes
    public void correctlySaveFieldRes() {
        when(fieldResRep.save(any(FieldReservation.class)))
                .then(returnsFirstArg());
        String response = fieldResSer
                .addNewFieldRes(fr1.getId(), fr1.getFieldId(),
                        fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals("Saved", response);
    }

    @Test
    public void correctlyDeleteFieldRes() {
        doNothing().when(fieldResRep).deleteById(any(Integer.class));
        String response = fieldResSer.deleteFieldRes(fr1.getId());
        assertEquals("Deleted FieldReservation", response);
    }

    @Test
    public void nullId() {
        fr1.setId(null);
        String response = fieldResSer.addNewFieldRes(fr1.getId(), fr1.getFieldId(),
                fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals(nullParameterError,
                response);
    }

    @Test
    public void nullFieldId() {
        fr1.setFieldId(null);
        String response = fieldResSer.addNewFieldRes(fr1.getId(), fr1.getFieldId(),
                fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals(nullParameterError,
                response);
    }

    @Test
    public void nullReserver() {
        fr1.setReserver(null);
        String response = fieldResSer.addNewFieldRes(fr1.getId(), fr1.getFieldId(),
                fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals(nullParameterError,
                response);
    }

    @Test
    public void nullStartTime() {
        fr1.setStartTime(null);
        String response = fieldResSer.addNewFieldRes(fr1.getId(), fr1.getFieldId(),
                fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals(nullParameterError,
                response);
    }

    @Test
    public void nullGroup() {
        fr1.setGroup(null);
        String response = fieldResSer.addNewFieldRes(fr1.getId(), fr1.getFieldId(),
                fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals(nullParameterError,
                response);
    }

    @Test
    public void correctlyUpdatesFieldRes() {
        when(fieldResRep.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(fr1));
        when(fieldResRep.save((any(FieldReservation.class))))
                .then(returnsFirstArg());
        String response = fieldResSer.updateFieldReservation(
                fr1.getId(), fr1.getFieldId(),
                fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals("updated", response);
    }

    @Test
    public void updateNonExistingFieldRes() {
        when(fieldResRep.findById(not(eq(2))))
                .thenReturn(Optional.ofNullable(null));
        String response = fieldResSer
                .updateFieldReservation(fr1.getId(), fr1.getFieldId(),
                        fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals("FieldReservation does not exist", response);
    }

    @Test
    public void updatesWitFieldNull() {
        when(fieldResRep.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(fr1));

        fr1.setFieldId(null);
        String response = fieldResSer.updateFieldReservation(
                fr1.getId(), fr1.getFieldId(),
                fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals(nullParameterError, response);
    }

    @Test
    public void updatesReserverNull() {
        when(fieldResRep.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(fr1));
        fr1.setReserver(null);
        String response = fieldResSer.updateFieldReservation(
                fr1.getId(), fr1.getFieldId(),
                fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals(nullParameterError, response);
    }

    @Test
    public void updatesStartNull() {
        when(fieldResRep.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(fr1));
        fr1.setStartTime(null);
        String response = fieldResSer.updateFieldReservation(
                fr1.getId(), fr1.getFieldId(),
                fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals(nullParameterError, response);
    }

    @Test
    public void updatesGroupNull() {
        when(fieldResRep.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(fr1));
        fr1.setGroup(null);
        String response = fieldResSer.updateFieldReservation(
                fr1.getId(), fr1.getFieldId(),
                fr1.getReserver(), fr1.getStartTime(), fr1.getGroup());
        assertEquals(nullParameterError, response);
    }


    @Test
    public void correctlyReturnAllFieldRes() {
        when(fieldResRep.findAll())
                .thenReturn(list);
        ArrayList<FieldReservation> expected = new ArrayList<>();
        expected.add(fr1);
        expected.add(fr2);
        expected.add(fr3);
        expected.add(fr4);
        Iterable<FieldReservation> response = fieldResSer.getAllFieldRes();
        assertEquals(expected, response);
    }

    @Test
    public void deleteNonExistingFieldRes() {
        doThrow(new EmptyResultDataAccessException(1)). //1 refers to the size
                when(fieldResRep).deleteById(any(Integer.class));
        String response = fieldResSer.deleteFieldRes(fr1.getId());
        assertEquals("FAILED, FieldReservation does not exist", response);
    }

    @Test
    public void correctlyReturnUserFieldRes() {
        when(fieldResRep.findAll())
                .thenReturn(list);
        ArrayList<FieldReservation> expected = new ArrayList<>();
        expected.add(fr3);
        expected.add(fr4);
        Iterable<FieldReservation> response = fieldResSer.userReservations(17);
        assertEquals(expected, response);
    }

    @Test
    public void correctlyReturnFieldResOfField() {
        when(fieldResRep.findAll())
                .thenReturn(list);
        ArrayList<FieldReservation> expected = new ArrayList<>();
        expected.add(fr1);
        expected.add(fr2);
        Iterable<FieldReservation> response = fieldResSer.getReservationsForField(35);
        assertEquals(expected, response);
    }

}


