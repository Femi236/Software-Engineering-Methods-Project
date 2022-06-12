package nl.tudelft.sem.template.reservations.services;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.entities.ReservationFactory;
import nl.tudelft.sem.template.reservations.repositories.EquipmentReservationRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EquipmentReservationService {
    //@Autowired
    private transient EquipmentReservationRepository equipmentReservationRepository;

    public EquipmentReservationService(
            EquipmentReservationRepository equipmentReservationRepository) {
        this.equipmentReservationRepository = equipmentReservationRepository;
    }

    /**
     * Creates a new EquipmentReservation.
     *
     * @param id of the EquipmentReservation
     * @param equipmentId id of the equipment reserved
     * @param reserver id of the reserver
     * @param startTime of the EquipmentReservation
     * @return the status of the request
     */
    public String addNewEquipmentRes(Integer id, Integer equipmentId,
                                                   Integer reserver, LocalDateTime startTime) {
        if (id == null) {
            return "Id was null";
        }
        if (equipmentId == null || reserver == null || startTime == null) {
            return "Parameters cannot be null";
        }
        ReservationFactory reservationFactory = new ReservationFactory();
        EquipmentReservation er =
                (EquipmentReservation) reservationFactory.createReservation("equipment");
        er.setId(id);
        er.setEquipmentId(equipmentId);
        er.setReserver(reserver);
        er.setStartTime(startTime);
        equipmentReservationRepository.save(er);
        return "Saved";
    }

    /**
     * Gets all EquipmentReservations.
     *
     * @return the status of the request
     */
    public Iterable<EquipmentReservation> getAllEquipmentRes() {
        return equipmentReservationRepository.findAll();
    }


    /**
     * Gets all the reservations for a single piece of equipment.
     *
     * @param id the equipment id
     * @return the list of reservations
     */
    public Iterable<EquipmentReservation> getReservationsForEquipment(int id) {
        Iterable<EquipmentReservation> allEquipmentReservations =
                equipmentReservationRepository.findAll();
        List<EquipmentReservation> res = new LinkedList<>();
        for (EquipmentReservation e : allEquipmentReservations) {
            if (e.getEquipmentId() == id) {
                res.add(e);
            }
        }
        return res;
    }

    /**
     * Gets all the EquipmentReservations of a certain user.
     *
     * @param id the id of the user
     * @return all the EquipmentReservation of the user
     */
    public Iterable<EquipmentReservation> userReservations(int id) {

        Iterable<EquipmentReservation> allEquipmentReservations =
                equipmentReservationRepository.findAll();
        List<EquipmentReservation> res = new LinkedList<>();
        for (EquipmentReservation e : allEquipmentReservations) {
            if (e.getReserver() == id) {
                res.add(e);
            }
        }
        return res;
    }



    /**.
     * Updates EquipmentReservation
     *
     * @param id of the EquipmentReservation
     * @param equipmentId id of the equipment reserved
     * @param reserver id of the reserver
     * @param startTime of the EquipmentReservation
     * @return the status of the request
     */
    public String updateEquipmentReservation(Integer id, Integer equipmentId, Integer reserver,
                             LocalDateTime startTime) {
        EquipmentReservation er = equipmentReservationRepository.findById(id).orElse(null);
        if (er == null) {
            return "EquipmentReservation does not exist";
        }
        if (equipmentId == null || reserver == null || startTime == null) {
            return "Parameters cannot be null";
        }
        er.setEquipmentId(equipmentId);
        er.setReserver(reserver);
        er.setStartTime(startTime);
        equipmentReservationRepository.save(er);
        return "updated";

    }


    /**
     * Deletes an EquipmentReservation by id.
     *
     * @param id of the EquipmentReservation
     * @return status of request
     */
    public String deleteEquipmentRes(int id) {
        try {
            equipmentReservationRepository.deleteById(id);
            return "Deleted EquipmentReservation";
        } catch (EmptyResultDataAccessException e) {
            return "FAILED, EquipmentReservation does not exist";
        }

    }

}
