package nl.tudelft.sem.template.gateway.controllers;

import nl.tudelft.sem.template.gateway.services.FieldCommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/gateway")
public class FieldCommunicationController {

    private final transient String loginError = "LoginError";
    private static final transient String authorization = "Authorization";

    @Autowired
    private transient FieldCommunicationService communicationService;

    @PostMapping(path = "/admin/field/addField")
    public @ResponseBody
    String addField(@RequestParam int id, @RequestParam String name,
                    @RequestParam int minCapacity, @RequestParam int maxCapacity) {
        return communicationService.addField(id, name, minCapacity, maxCapacity);
    }

    @PostMapping(path = "/admin/field/changeCapacity")
    public @ResponseBody
    String changeCapacity(@RequestParam int id, @RequestParam int maxCapacity) {
        return communicationService.changeCapacity(id, maxCapacity);
    }

    @DeleteMapping(path = "/admin/deleteField")
    public @ResponseBody
    String deleteField(@RequestHeader(name = authorization) String token, @RequestParam int id) {
        return communicationService.deleteField(id, token);
    }

}
