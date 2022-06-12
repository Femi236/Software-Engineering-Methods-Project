package nl.tudelft.sem.template.groups.controllers;

import nl.tudelft.sem.template.groups.entities.Group;
import nl.tudelft.sem.template.groups.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/group") // This means URL's start with /demo (after Application path)
public class GroupController {

    @Autowired
    private transient GroupService groupService;

    /**
     * Creates a new Group.
     *
     * @param creator the user that created the group
     * @return the state of the request
     */
    @PostMapping(path = "/add") // Map ONLY POST Requests
    public @ResponseBody
    String addNewGroup(@RequestParam int creator) {

        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        return groupService.addNewGroup(creator);
    }

    /**
     * Returns all Groups.
     *
     * @return an iterable of all Groups
     */
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Group> getAllGroups() {
        // This returns a JSON or XML with the users
        return groupService.getAllGroups();
    }

    /**
     * Updates a Group.
     *
     * @param id the id of the Group to update
     * @param creator the user that created the group to update
     * @return the status of the request
     */
    @PostMapping(path = "/update")
    public @ResponseBody String updateGroup(@RequestParam int id, @RequestParam int creator) {
        return groupService.updateGroup(id, creator);
    }

    /**
     * Deletes a Group by its id.
     *
     * @param id the id of the group to delete
     * @return the statys of the request
     */
    @PostMapping(path = "/delete")
    public @ResponseBody String deleteGroup(@RequestParam int id) {
        return groupService.deleteGroup(id);
    }



}
