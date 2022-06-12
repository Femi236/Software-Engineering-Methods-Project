package nl.tudelft.sem.template.groups.services;

import nl.tudelft.sem.template.groups.entities.Group;
import nl.tudelft.sem.template.groups.repositories.GroupRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class GroupService {
    //@Autowired
    private transient GroupRepository groupRepository;

    public GroupService(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    /**
     * Creates a new Group.
     *
     * @param creator the user that created the group
     * @return the state of the request
     */
    public String addNewGroup(int creator) {
        Group g = new Group();
        g.setCreator(creator);
        groupRepository.save(g);
        return "Saved";
    }

    /**
     * Returns all Groups.
     *
     * @return an iterable of all Groups
     */
    public Iterable<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    /**
     * Updates a Group.
     *
     * @param id the id of the Group to update
     * @param creator the user that created the group to update
     * @return the status of the request
     */
    public String updateGroup(int id, int creator) {
        Group findGroup = groupRepository.findById(id).orElse(null);
        if (findGroup == null) {
            return "Group does not exist";
        }
        // Check if user is in group
        Group g = new Group(id, creator);
        groupRepository.save(g);
        return "Updated";
    }

    /**
     * Deletes a Group by its id.
     *
     * @param id the id of the group to delete
     * @return the statys of the request
     */
    public String deleteGroup(int id) {
        try {
            groupRepository.deleteById(id);
            return "Deleted";
        } catch (EmptyResultDataAccessException e) {
            return "Group does not exist";
        }
    }

}

