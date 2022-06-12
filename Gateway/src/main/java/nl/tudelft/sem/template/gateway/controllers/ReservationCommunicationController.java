package nl.tudelft.sem.template.gateway.controllers;

import java.time.LocalDateTime;
import java.util.List;
import nl.tudelft.sem.template.gateway.services.ReservationCommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/gateway")
public class ReservationCommunicationController {

    private final transient String loginError = "LoginError";
    private static final transient String authorization = "Authorization";

    @Autowired
    private transient ReservationCommunicationService communicationService;

    @GetMapping(path = "/getEquipmentReservations")
    public @ResponseBody List<Object>
    getEquipmentReservations(@RequestHeader(name = authorization) String token) {
        return communicationService.getEquipmentReservations(token);

    }

    @GetMapping(path = "/admin/getEquipmentReservationsForEquipment")
    public @ResponseBody List<Object>
    getEquipmentReservationsForEquipment(@RequestParam int id) {
        return communicationService.getEquipmentReservationsForEquipment(id);
    }

    @PostMapping(path = "/singleFieldReservation")
    public @ResponseBody String singleFieldReservation(
            @RequestHeader(name = authorization) String token, @RequestParam int id,
            @RequestParam int fieldId,
            @RequestParam @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime) {
        return communicationService.singleFieldReservation(token, id, fieldId, startTime);
    }

    @PostMapping(path = "/groupFieldReservation")
    public @ResponseBody String groupFieldReservation(
            @RequestHeader(name = authorization) String token, @RequestParam int id,
            @RequestParam int fieldId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime startTime, @RequestParam int group) {
        return communicationService.groupFieldReservation(token, id, fieldId, startTime, group);
    }

    @PostMapping(path = "/equipmentReservation")
    public @ResponseBody String equipmentReservation(
            @RequestHeader(name = authorization) String token, @RequestParam int id,
            @RequestParam int equipmentId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                    LocalDateTime startTime) {
        return communicationService.equipmentReservation(token, id, equipmentId, startTime);
    }

    @PostMapping(path = "/deleteEquipmentReservation")
    public @ResponseBody
    String deleteEquipRes(int id) {
        return communicationService.deleteEquipRes(id);
    }

    @PostMapping(path = "/deleteFieldReservation")
    public @ResponseBody
    String deleteFieldRes(int id) {
        return communicationService.deleteFieldRes(id);
    }

    @GetMapping(path = "/admin/allEquipmentReservations")
    public @ResponseBody
    List<Object> getAllEquipRes() {
        return communicationService.getAllEquipRes();
    }

    @GetMapping(path = "/admin/allFieldReservations")
    public @ResponseBody
    List<Object> getAllFieldRes() {
        return communicationService.getAllFieldRes();
    }

    @GetMapping(path = "/getFieldReservations")
    public @ResponseBody List<Object>
    getFieldReservations(@RequestHeader(name = authorization) String token) {
        return communicationService.getFieldReservations(token);
    }
}
