package nl.tudelft.sem.template.authentication;

import java.util.Arrays;
import java.util.HashSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private transient BCryptPasswordEncoder encoder;

    @Autowired
    private transient UserRepository userRepository;

    @Override
    public User saveUser(User user) throws NetIdAlreadyUsedException {
        if (isUserAlreadyPresent(user.getNetId())) {
            throw new NetIdAlreadyUsedException();
        }
        user.setPassword(encoder.encode(user.getPassword()));
        user.setRole(user.getRole());
        return userRepository.save(user);
    }

    @Override
    public boolean isUserAlreadyPresent(String netId) {
        if (netId == null) {
            throw new IllegalArgumentException();
        }
        boolean isUserAlreadyExists;
        User existingUser = userRepository.findByNetId(netId);
        // If user is found in database, then user already exists.
        if (existingUser != null) {
            isUserAlreadyExists = true;
        } else {
            isUserAlreadyExists = false;
        }
        return isUserAlreadyExists;
    }

    @Override
    public UserDetails loadUserByUsername(String netId) {
        User user = userRepository.findByNetId(netId);
        if (user == null) {
            throw new UsernameNotFoundException(netId);
        }
        return new AuthenticationPrinciple(user, encoder);

    }
}
