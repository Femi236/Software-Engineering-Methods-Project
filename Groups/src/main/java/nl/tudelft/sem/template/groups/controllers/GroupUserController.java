package nl.tudelft.sem.template.groups.controllers;

import nl.tudelft.sem.template.groups.entities.GroupUser;
import nl.tudelft.sem.template.groups.services.GroupUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/groupUser") // This means URL's start with /demo (after Application path)
public class GroupUserController {

    @Autowired
    private transient GroupUserService groupUserService;

    /**
     * Creates a new GroupUser.
     *
     * @param group the group
     * @param user the user
     * @return the status of the request
     */
    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody String addNewGroupUser(@RequestParam int group, @RequestParam int user) {

        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        return groupUserService.addNewGroupUser(group, user);
    }

    /**
     * Returns all GroupsUsers.
     *
     * @return the status of the request
     */
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<GroupUser> getAllGroupUsers() {
        // This returns a JSON or XML with the users
        return groupUserService.getAllGroupUsers();
    }

    /**
     * Deletes a GroupUser.
     *
     * @param group the group
     * @param user the user
     * @return the status of the request
     */
    @PostMapping(path = "/delete")
    public @ResponseBody String deleteGroupUser(@RequestParam int group, @RequestParam int user) {
        return groupUserService.deleteGroupUser(group, user);
    }



}
