package nl.tudelft.sem.template.authentication;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public User saveUser(User user) throws NetIdAlreadyUsedException;

    public boolean isUserAlreadyPresent(String netId);


}
