package nl.tudelft.sem.template.reservations.controllers;


import java.time.LocalDateTime;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.services.EquipmentReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping
        // This means URL's start with /demo (after Application path)
        (path = "/equipmentreservation")

public class EquipmentReservationController {

    @Autowired
    private transient EquipmentReservationService equipmentReservationService;


    /**
     * Creates a new EquipmentReservation.
     *
     * @param id the EquipmentReservationId
     * @param equipmentId the equipmentId
     * @param reserver the reserverId
     * @param startTime the startTime of the EquipmentReservation
     * @return the status of the request
     */
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public String addNewEquipmentRes(@RequestParam int id,
                                                   @RequestParam int equipmentId,
                                                   @RequestParam int reserver,
                                                   @RequestParam @DateTimeFormat
                                                           (iso = DateTimeFormat.ISO.DATE_TIME)
                                                               LocalDateTime startTime) {

        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        equipmentReservationService.addNewEquipmentRes(id, equipmentId, reserver, startTime);
        return "Saved";
    }

    /**
     * Gets all EquipmentReservations.
     *
     * @return the status of the response
     */
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<EquipmentReservation> getAllEquipmentRes() {
        // This returns a JSON or XML with the users
        return equipmentReservationService.getAllEquipmentRes();
    }

    /**
     * Gets all the reservations for a single piece of equipment.
     *
     * @param id the equipment id
     * @return the list of reservations
     */
    @PostMapping(path = "/reservationsForEquipment")
    @ResponseBody
    public Iterable<EquipmentReservation> reservationsForEquipment(@RequestParam int id) {
        // This returns a JSON or XML with the users
        return equipmentReservationService.getReservationsForEquipment(id);
    }

    /**
     * Gets all the reservations for a user.
     *
     * @param id the id of the user
     * @return the list of reservations
     */
    @PostMapping(path = "/userReservations")
    @ResponseBody
    public Iterable<EquipmentReservation> userReservations(@RequestParam int id) {
        // This returns a JSON or XML with the users
        return equipmentReservationService.userReservations(id);
    }


    /**
     * Updates an EquipmentReservation.
     *
     * @param id the id of the EquipmentReservation to update
     * @param equipmentId the id of the equipment to update
     * @param reserver the id of the reserver to update
     * @param startTime the startTime of the EquipmentReservation to update
     * @return the status of the request
     */
    @PostMapping(path = "/update")
    @ResponseBody
    public String updateEquipmentRes(@RequestParam int id, @RequestParam int equipmentId,
                                              @RequestParam int reserver,
                                              @RequestParam @DateTimeFormat
                                                      (iso = DateTimeFormat.ISO.DATE_TIME)
                                                          LocalDateTime startTime) {
        return equipmentReservationService.updateEquipmentReservation(id, equipmentId,
                reserver, startTime);
    }

    /**
     * Deletes an EquipmentReservation by id.
     *
     * @param id the id of the EquipmentReservation to be deleted
     */
    @PostMapping(path = "/delete")
    @ResponseBody
    public String deleteEquipmentRes(@RequestParam int id) {
        return equipmentReservationService.deleteEquipmentRes(id);
    }



}
