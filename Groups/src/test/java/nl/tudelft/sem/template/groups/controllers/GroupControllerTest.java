package nl.tudelft.sem.template.groups.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.groups.entities.Group;
import nl.tudelft.sem.template.groups.services.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@WebMvcTest(controllers = GroupController.class)
public class GroupControllerTest {

    private transient Group group1;
    private transient Group group2;
    private transient Group group3;

    private final transient String groupParam1 = "id";
    private final transient String groupParam2 = "creator";

    private final transient String addGroupPath = "/group/add";
    private final transient String getGroupPath = "/group/all";
    private final transient String updateGroupPath = "/group/update";
    private final transient String deleteGroupPath = "/group/delete";

    public final transient String contentType = "application/json";

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    @MockBean
    private transient GroupService groupService;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        group1 = new Group(1, 4);
        group2 = new Group(2, 3);
        group3 = new Group(3, 4);
    }

    @Test
    public void whenValidInput_CapturesRightInput() throws Exception {
        mockMvc.perform(post(addGroupPath).contentType(contentType)
                .param(groupParam2, Integer.toString(group1.getCreator())))
                .andExpect(status().isOk());
        ArgumentCaptor<Integer> creatorCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(groupService, times(1)).addNewGroup(creatorCaptor.capture());
        assertEquals(creatorCaptor.getValue(), group1.getCreator());
    }

    @Test
    public void whenValidInput_thenSavesGroup() throws Exception {
        when(groupService.addNewGroup(any(Integer.class))).thenReturn("Saved");
        MvcResult mvcResult = mockMvc.perform(post(addGroupPath).contentType(contentType)
                .param(groupParam2, Integer.toString(group1.getCreator())))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("Saved", response);
    }

    @Test
    public void returnsRightListOfGroups() throws Exception {
        List<Group> allGroups = new ArrayList<>();
        allGroups.add(group1);
        allGroups.add(group2);
        allGroups.add(group3);

        when(groupService.getAllGroups()).thenReturn(allGroups);

        MvcResult mvcResult = mockMvc.perform(get(getGroupPath).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(allGroups)).isEqualToIgnoringWhitespace(res);
    }

    @Test
    public void whenValidInput_thenReturnsUpdated() throws Exception {
        when(groupService.updateGroup(any(Integer.class), any(Integer.class)))
                .thenReturn("Updated");
        MvcResult mvcResult = mockMvc.perform(post(updateGroupPath).contentType(contentType)
                .param(groupParam1, Integer.toString(group1.getId()))
                .param(groupParam2, Integer.toString(group1.getCreator())))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Updated", response);
    }

    @Test
    public void whenValidInput_thenReturnsDeleted() throws Exception {
        when(groupService.deleteGroup(any(Integer.class))).thenReturn("Deleted");
        MvcResult mvcResult = mockMvc.perform(post(deleteGroupPath).contentType(contentType)
                .param(groupParam1, Integer.toString(group1.getId())))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Deleted", response);
    }

}
