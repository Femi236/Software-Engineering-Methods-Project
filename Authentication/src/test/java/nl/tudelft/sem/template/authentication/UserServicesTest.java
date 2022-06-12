package nl.tudelft.sem.template.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

@ExtendWith(MockitoExtension.class)
public class UserServicesTest {

    private transient User user1;
    private transient User user2;
    private transient User user3;

    @Mock
    private transient UserRepository userRepository;

    @InjectMocks
    private transient UserServices userServices;


    /**before each to be called before each method.
     *
     */
    @BeforeEach
    public void init() {
        user1 = new User(1, "test", "best", "password", "user", "123456");
        user2 = new User(2, "test2", "best2", "password", "user", "333333");
        user3 = new User(3, "test3", "best3", "password", "user", "246890");
    }

    @Test
    public void correctlySavesUser() {
        when(userRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(user1));
        when(userRepository.save(any(User.class))).then(returnsFirstArg());
        String response = userServices
                .updateUser(user1.getId(), user1.getFirstName(), user1.getLastName(),
                        user1.getPassword(), user1.getRole(), user1.getNetId());
        assertEquals("saved", response);
    }





}
