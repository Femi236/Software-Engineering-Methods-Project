package nl.tudelft.sem.template.groups.services;

import nl.tudelft.sem.template.groups.entities.GroupUser;
import nl.tudelft.sem.template.groups.entities.GroupUserId;
import nl.tudelft.sem.template.groups.repositories.GroupUserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class GroupUserService {
    //@Autowired
    private transient GroupUserRepository groupUserRepository;

    public GroupUserService(GroupUserRepository groupUserRepository) {
        this.groupUserRepository = groupUserRepository;
    }

    /**
     * Creates a new GroupUser.
     *
     * @param group the group
     * @param user the user
     * @return the status of the request
     */
    public String addNewGroupUser(int group, int user) {
        GroupUser g = new GroupUser();
        g.setGroup(group);
        g.setUser(user);
        groupUserRepository.save(g);
        return "Saved";
    }

    /**
     * Returns all GroupsUsers.
     *
     * @return the status of the request
     */
    public Iterable<GroupUser> getAllGroupUsers() {
        return groupUserRepository.findAll();
    }

    /**
     * Deletes a GroupUser.
     *
     * @param group the group
     * @param user the user
     * @return the status of the request
     */
    public String deleteGroupUser(int group, int user) {
        GroupUserId id = new GroupUserId(group, user);
        try {
            groupUserRepository.deleteById(id);
            return "Deleted";
        } catch (EmptyResultDataAccessException e) {
            return "GroupUser does not exist";
        }
    }

}
