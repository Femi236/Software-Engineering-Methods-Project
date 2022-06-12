package nl.tudelft.sem.template.groups.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.groups.entities.GroupUser;
import nl.tudelft.sem.template.groups.entities.GroupUserId;
import nl.tudelft.sem.template.groups.repositories.GroupUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

@ExtendWith(MockitoExtension.class)
public class GroupUserServiceTest {

    private transient GroupUser groupUser1;
    private transient GroupUser groupUser2;
    private transient GroupUser groupUser3;
    private transient List<GroupUser> groupUserList;

    @Mock
    private transient GroupUserRepository groupUserRepository;

    @InjectMocks
    private transient GroupUserService groupUserService;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        groupUser1 = new GroupUser(1, 1);
        groupUser2 = new GroupUser(1, 2);
        groupUser3 = new GroupUser(2, 1);

        groupUserList = new ArrayList<>();
        groupUserList.add(groupUser1);
        groupUserList.add(groupUser2);
        groupUserList.add(groupUser3);
    }

    @Test
    public void savesValidGroupUser() {
        when(groupUserRepository.save(any(GroupUser.class))).then(returnsFirstArg());
        String response = groupUserService
                .addNewGroupUser(groupUser1.getGroup(), groupUser1.getUser());
        assertEquals("Saved", response);
    }

    @Test
    public void returnsCorrectGroupUser() {
        when(groupUserRepository.findAll()).thenReturn(groupUserList);
        Iterable<GroupUser> response = groupUserService.getAllGroupUsers();
        assertEquals(groupUserList, response);
    }

    @Test
    public void deletesValidGroupUser() {
        doNothing().when(groupUserRepository).deleteById(any(GroupUserId.class));
        String response = groupUserService
                .deleteGroupUser(groupUser3.getGroup(), groupUser3.getUser());
        assertEquals("Deleted", response);
    }

    @Test
    public void deleteNonExistingGroupUser() {
        doThrow(new EmptyResultDataAccessException(1)).when(groupUserRepository)
                .deleteById(any(GroupUserId.class));
        String response = groupUserService
                .deleteGroupUser(groupUser1.getGroup(), groupUser1.getUser());
        assertEquals("GroupUser does not exist", response);
    }

}
