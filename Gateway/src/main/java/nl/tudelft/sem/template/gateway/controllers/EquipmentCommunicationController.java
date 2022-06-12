package nl.tudelft.sem.template.gateway.controllers;

import java.util.List;
import nl.tudelft.sem.template.gateway.services.EquipmentCommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/gateway")
public class EquipmentCommunicationController {

    private final transient String loginError = "LoginError";
    private static final transient String authorization = "Authorization";

    @Autowired
    private transient EquipmentCommunicationService communicationService;

    @PostMapping(path = "/addEquipment")
    public @ResponseBody
    String addNewEquipment(@RequestParam int type) {
        return communicationService.addNewEquipment(type);
    }

    @GetMapping(path = "/allEquipment")
    public @ResponseBody
    List<Object> getAllEquipment1() {
        return communicationService.getAllEquipment();
    }

    @PostMapping(path = "/admin/equipment/addEquipment")
    public @ResponseBody
    String addEquipment(@RequestParam int type) {
        return communicationService.addEquipment(type);
    }

    @PostMapping(path = "/admin/addEquipmentType")
    public @ResponseBody
    String addNewType(@RequestParam String type) {
        return communicationService.addNewEquipType(type);
    }

    @DeleteMapping(path = "/admin/deleteEquipment")
    public @ResponseBody
    String deleteEquipment(@RequestHeader(name = authorization)
                                   String token, @RequestParam int id) {
        return communicationService.deleteEquipment(id, token);
    }

}
