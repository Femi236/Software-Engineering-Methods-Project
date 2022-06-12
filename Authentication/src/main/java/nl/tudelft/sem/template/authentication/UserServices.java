package nl.tudelft.sem.template.authentication;

import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServices {

    private transient UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** update user method.
     *
     * @param id of the user
     * @param firstName of the user
     * @param lastName of the user
     * @param password of the user
     * @param role of the user
     * @param netId of the user
     * @return updated user
     */
    public String updateUser(int id, String firstName, String lastName,
                             String password, String role, String netId) {
        if (userRepository.findById(id).isPresent()) {
            User oldUser = userRepository.findById(id).get();
            oldUser.setNetId(netId);
            oldUser.setFirstName(firstName);
            oldUser.setLastName(lastName);
            oldUser.setPassword(password);
            oldUser.setRole(role);
            userRepository.save(oldUser);
            return "saved";
        } else {
            throw new EntityNotFoundException(
                    "User not found for parameters {id=" + id + "}."
            );
        }
    }


}
