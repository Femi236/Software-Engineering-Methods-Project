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
import nl.tudelft.sem.template.groups.entities.GroupUser;
import nl.tudelft.sem.template.groups.services.GroupUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = GroupUserController.class)
public class GroupUserControllerTest {

    private transient GroupUser groupUser1;
    private transient GroupUser groupUser2;
    private transient GroupUser groupUser3;

    private final transient String groupUserParam1 = "group";
    private final transient String groupUserParam2 = "user";

    private final transient String addGroupUserPath = "/groupUser/add";
    private final transient String getGroupUserPath = "/groupUser/all";
    private final transient String deleteGroupUserPath = "/groupUser/delete";

    public final transient String contentType = "application/json";

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    @MockBean
    private transient GroupUserService groupUserService;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        groupUser1 = new GroupUser(1, 1);
        groupUser2 = new GroupUser(1, 2);
        groupUser3 = new GroupUser(2, 1);
    }

    @Test
    public void whenValidInput_CapturesRightInput() throws Exception {
        mockMvc.perform(post(addGroupUserPath).contentType(contentType)
                .param(groupUserParam1, Integer.toString(groupUser1.getGroup()))
                .param(groupUserParam2, Integer.toString(groupUser1.getUser())))
                .andExpect(status().isOk());
        ArgumentCaptor<Integer> groupCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> userCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(groupUserService, times(1))
                .addNewGroupUser(groupCaptor.capture(), userCaptor.capture());
        assertEquals(groupCaptor.getValue(), groupUser1.getGroup());
        assertEquals(userCaptor.getValue(), groupUser1.getUser());
    }

    @Test
    public void whenValidInput_thenSavesGroupUser() throws Exception {
        when(groupUserService.addNewGroupUser(any(Integer.class), any(Integer.class)))
                .thenReturn("Saved");
        MvcResult mvcResult = mockMvc.perform(post(addGroupUserPath).contentType(contentType)
                .param(groupUserParam1, Integer.toString(groupUser1.getGroup()))
                .param(groupUserParam2, Integer.toString(groupUser1.getUser())))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("Saved", response);
    }

    @Test
    public void returnsRightListOfGroupUsers() throws Exception {
        List<GroupUser> allGroupUsers = new ArrayList<>();
        allGroupUsers.add(groupUser1);
        allGroupUsers.add(groupUser2);
        allGroupUsers.add(groupUser3);

        when(groupUserService.getAllGroupUsers()).thenReturn(allGroupUsers);

        MvcResult mvcResult = mockMvc.perform(get(getGroupUserPath).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(allGroupUsers)).isEqualToIgnoringWhitespace(res);
    }

    @Test
    public void whenValidInput_thenReturnsDeleted() throws Exception {
        when(groupUserService.deleteGroupUser(any(Integer.class), any(Integer.class)))
                .thenReturn("Deleted");
        MvcResult mvcResult = mockMvc.perform(post(deleteGroupUserPath).contentType(contentType)
                .param(groupUserParam1, Integer.toString(groupUser1.getGroup()))
                .param(groupUserParam2, Integer.toString(groupUser1.getUser())))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Deleted", response);
    }

}
