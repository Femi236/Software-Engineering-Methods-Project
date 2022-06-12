package nl.tudelft.sem.template.groups.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.groups.entities.Group;
import nl.tudelft.sem.template.groups.repositories.GroupRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {
    private transient Group group1;
    private transient Group group2;
    private transient Group group3;
    private transient List<Group> groupList;

    @Mock
    private transient GroupRepository groupRepository;

    @InjectMocks
    private transient GroupService groupService;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        group1 = new Group(1, 1);
        group2 = new Group(2, 2);
        group3 = new Group(2, 1);

        groupList = new ArrayList<>();
        groupList.add(group1);
        groupList.add(group2);
        groupList.add(group3);
    }

    @Test
    public void savesValidGroup() {
        when(groupRepository.save(any(Group.class))).then(returnsFirstArg());
        String response = groupService.addNewGroup(group1.getCreator());
        assertEquals("Saved", response);
    }

    @Test
    public void returnsCorrectGroup() {
        when(groupRepository.findAll()).thenReturn(groupList);
        Iterable<Group> response = groupService.getAllGroups();
        assertEquals(groupList, response);
    }

    @Test
    public void updatesValidGroup() {
        when(groupRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(group2));
        when(groupRepository.save(any(Group.class))).then(returnsFirstArg());
        String response = groupService.updateGroup(group2.getId(), group1.getCreator());
        assertEquals("Updated", response);
    }

    @Test
    public void updateNonExistingGroup() {
        when(groupRepository.findById(any(Integer.class))).thenReturn(Optional.ofNullable(null));
        String response = groupService.updateGroup(group2.getId(), group1.getCreator());
        assertEquals("Group does not exist", response);
    }

    @Test
    public void deletesValidGroup() {
        doNothing().when(groupRepository).deleteById(any(Integer.class));
        String response = groupService.deleteGroup(group3.getId());
        assertEquals("Deleted", response);
    }

    @Test
    public void deleteNonExistingGroup() {
        doThrow(new EmptyResultDataAccessException(1)).when(groupRepository)
                .deleteById(any(Integer.class));
        String response = groupService.deleteGroup(group1.getId());
        assertEquals("Group does not exist", response);
    }

}
