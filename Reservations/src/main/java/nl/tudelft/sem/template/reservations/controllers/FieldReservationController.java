package nl.tudelft.sem.template.reservations.controllers;


import java.time.LocalDateTime;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.services.FieldReservationService;
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
        (path = "/fieldreservation")

public class FieldReservationController {

    @Autowired
    private transient FieldReservationService fieldReservationService;


    /**
     * Creates a new FieldReservation.
     *
     * @param id the fieldReservationId
     * @param fieldId the fieldId
     * @param reserver the reserverId
     * @param startTime the startTime of the FieldReservation
     * @param group the groupId
     * @return the status of the request
     */
    @PostMapping(path = "/add") // Map ONLY POST Requests
    @ResponseBody
    public String addNewFieldRes(@RequestParam int id,
                                     @RequestParam int fieldId,
                                     @RequestParam int reserver,
                                     @RequestParam @DateTimeFormat
                                             (iso = DateTimeFormat.ISO.DATE_TIME)
                                             LocalDateTime startTime,
                                 @RequestParam int group) {

        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        fieldReservationService.addNewFieldRes(id, fieldId, reserver, startTime, group);
        return "Saved";
    }

    /**
     * Gets all FieldReservations.
     *
     * @return the status of the response
     */
    @GetMapping(path = "/all")
    @ResponseBody
    public Iterable<FieldReservation> getAllFieldRes() {
        // This returns a JSON or XML with the users
        return fieldReservationService.getAllFieldRes();
    }

    /**
     * Updates an FieldReservation.
     *
     * @param id the id of the FieldReservation to update
     * @param fieldId the id of the field to update
     * @param reserver the id of the reserver to update
     * @param startTime the startTime of the FieldReservation to update
     * @param group the id of the group
     * @return the status of the request
     */
    @PostMapping(path = "/update")
    @ResponseBody
    public String updateFieldRes(@RequestParam int id, @RequestParam int fieldId,
                                  @RequestParam int reserver,
                                  @RequestParam @DateTimeFormat
                                          (iso = DateTimeFormat.ISO.DATE_TIME)
                                          LocalDateTime startTime,
                                         @RequestParam int group) {
        return fieldReservationService.updateFieldReservation(id, fieldId, reserver,
                startTime, group);
    }

    /**
     * Deletes an FieldReservation by id.
     *
     * @param id the id of the FieldReservation to be deleted
     */
    @PostMapping(path = "/delete")
    @ResponseBody
    public String deleteFieldRes(@RequestParam int id) {
        return fieldReservationService.deleteFieldRes(id);
    }


    @PostMapping(path = "/userReservations")
    @ResponseBody
    public Iterable<FieldReservation> userReservations(@RequestParam int id) {
        // This returns a JSON or XML with the users
        return fieldReservationService.userReservations(id);
    }

}
