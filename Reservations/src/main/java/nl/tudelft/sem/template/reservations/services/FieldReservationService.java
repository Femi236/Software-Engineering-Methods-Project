package nl.tudelft.sem.template.reservations.services;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.entities.ReservationFactory;
import nl.tudelft.sem.template.reservations.repositories.FieldReservationRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class FieldReservationService {
    //@Autowired
    private transient FieldReservationRepository fieldReservationRepository;

    public FieldReservationService(
            FieldReservationRepository fieldReservationRepository) {
        this.fieldReservationRepository = fieldReservationRepository;
    }

    /**
     * Creates a new FieldReservation.
     *
     * @param id of the fieldReservation
     * @param fieldId id of the field reserved
     * @param reserver id of the reserver
     * @param startTime of the FieldReservation
     * @param group id of the group who made the reservation
     * @return the status of the request
     */
    public String addNewFieldRes(Integer id, Integer fieldId, Integer reserver,
                                 LocalDateTime startTime, Integer group) {
        if (id == null || fieldId == null || reserver == null
                || startTime == null || group == null) {
            return "Parameters cannot be null";
        }

        ReservationFactory reservationFactory = new ReservationFactory();
        FieldReservation fr = (FieldReservation) reservationFactory.createReservation("field");
        fr.setId(id);
        fr.setFieldId(fieldId);
        fr.setReserver(reserver);
        fr.setStartTime(startTime);
        fr.setGroup(group);
        fieldReservationRepository.save(fr);
        return "Saved";
    }

    /**
     * Gets all FieldReservations.
     *
     * @return the status of the request
     */
    public Iterable<FieldReservation> getAllFieldRes() {
        return fieldReservationRepository.findAll();
    }

    /**
     * Updates FieldReservation.
     *
     * @param id of the FieldReservation
     * @param fieldId id of the field reserved
     * @param reserver id of the reserver
     * @param startTime of the FieldReservation
     * @param group id of the group who made the reservation
     * @return the status of the request
     */
    public String updateFieldReservation(Integer id, Integer fieldId, Integer reserver,
                                             LocalDateTime startTime, Integer group) {
        FieldReservation fr = fieldReservationRepository.findById(id).orElse(null);
        if (fr == null) {
            return "FieldReservation does not exist";
        }
        if (fieldId == null || reserver == null || startTime == null || group == null) {
            return "Parameters cannot be null";
        }
        fr = fieldReservationRepository.findById(id).get();
        fr.setFieldId(fieldId);
        fr.setReserver(reserver);
        fr.setStartTime(startTime);
        fr.setGroup(group);
        fieldReservationRepository.save(fr);
        return "updated";

    }

    /**
     * Deletes an FieldReservation by id.
     *
     * @param id of the fieldReservation
     * @return status of request
     */
    public String deleteFieldRes(int id) {
        try {
            fieldReservationRepository.deleteById(id);
            return "Deleted FieldReservation";
        } catch (EmptyResultDataAccessException e) {
            return "FAILED, FieldReservation does not exist";
        }

    }

    /**
     * Gets all the FieldReservations of a certain user.
     *
     * @param id the id of the user
     * @return all the FieldReservation of the user
     */
    public Iterable<FieldReservation> userReservations(int id) {

        Iterable<FieldReservation> allFieldReservations =
                fieldReservationRepository.findAll();
        List<FieldReservation> res = new LinkedList<>();
        for (FieldReservation e : allFieldReservations) {
            if (e.getReserver() == id) {
                res.add(e);
            }
        }
        return res;
    }

    /**
     * Gets all the reservations for a field.
     *
     * @param id the field id
     * @return the list of reservations
     */
    public Iterable<FieldReservation> getReservationsForField(int id) {
        Iterable<FieldReservation> allFieldReservations =
                fieldReservationRepository.findAll();
        List<FieldReservation> res = new LinkedList<>();
        for (FieldReservation e : allFieldReservations) {
            if (e.getFieldId() == id) {
                res.add(e);
            }
        }
        return res;
    }

}
