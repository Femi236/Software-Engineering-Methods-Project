package nl.tudelft.sem.template.gateway.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.gateway.services.GroupsCommunicationService;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@WebMvcTest(controllers = GroupsCommunicationController.class)
public class GroupsControllerTest {

    private transient String authorization = "Authorization";
    private transient String applicationJson = "application/json";
    private transient String saved = "Saved";
    private transient String deleted = "Deleted";
    private transient String token = "token";

    private transient String defaultIntParam = "1";

    private transient String group = "group";

    private transient List<Object> objectList;
    private transient JSONArray jsonObjectList;

    @Autowired
    private transient MockMvc mockMvc;

    @MockBean
    private transient GroupsCommunicationService communicationService;

    /**
     * Initializer method.
     */
    @BeforeEach
    public void init() {
        objectList = new ArrayList<>();
        objectList.add("Object");
        jsonObjectList = new JSONArray(objectList.toString());
    }

    @Test
    @WithMockUser
    public void whenValidInput_addsGroupMember() throws Exception {
        when(communicationService.addGroupMember(any(Integer.class), any(Integer.class)))
                .thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/addGroupMember")
                .contentType(applicationJson)
                .param(group, defaultIntParam)
                .param("user", defaultIntParam))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_deletesGroupMember() throws Exception {
        when(communicationService.deleteGroupMember(any(Integer.class), any(Integer.class)))
                .thenReturn(deleted);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/deleteGroupMember")
                .contentType(applicationJson)
                .param(group, defaultIntParam)
                .param("user", defaultIntParam))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(deleted, res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_deletesGroup() throws Exception {
        when(communicationService.deleteGroup(any(Integer.class))).thenReturn(deleted);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/deleteGroup")
                .contentType(applicationJson)
                .param("id", defaultIntParam))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(deleted, res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_leaveGroup() throws Exception {
        when(communicationService.leaveGroup(any(String.class), any(Integer.class)))
                .thenReturn(deleted);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/leaveGroup")
                .contentType(applicationJson)
                .param(group, defaultIntParam)
                .header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(deleted, res);
    }

}
