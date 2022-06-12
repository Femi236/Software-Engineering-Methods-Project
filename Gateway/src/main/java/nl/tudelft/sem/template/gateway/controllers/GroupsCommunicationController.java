package nl.tudelft.sem.template.gateway.controllers;

import nl.tudelft.sem.template.gateway.services.GroupsCommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/gateway")
public class GroupsCommunicationController {

    private final transient String loginError = "LoginError";
    private static final transient String authorization = "Authorization";

    @Autowired
    private transient GroupsCommunicationService communicationService;

    @PostMapping(path = "/addGroupMember")
    public @ResponseBody
    String addGroupMember(@RequestParam int group, @RequestParam int user) {
        return communicationService.addGroupMember(group, user);
    }

    @PostMapping(path = "/deleteGroupMember")
    public @ResponseBody
    String deleteGroupMember(@RequestParam int group, @RequestParam int user) {
        return communicationService.deleteGroupMember(group, user);
    }

    @PostMapping(path = "/deleteGroup")
    public @ResponseBody
    String deleteGroup(@RequestParam int id) {
        return communicationService.deleteGroup(id);
    }

    @PostMapping(path = "/leaveGroup")
    public @ResponseBody
    String leaveGroup(@RequestHeader(name = "Authorization") String token,
                      @RequestParam int group) {
        return communicationService.leaveGroup(token, group);
    }

    @PostMapping(path = "/addGroup")
    public @ResponseBody
    String addNewGroup(@RequestHeader(name = authorization) String creatorToken) {
        return communicationService.addNewGroup(creatorToken);
    }

}
