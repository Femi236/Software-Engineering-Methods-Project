package nl.tudelft.sem.template.gateway.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.gateway.services.AuthenticationCommunicationService;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@WebMvcTest(controllers = AuthenticationCommunicationController.class)
public class AuthenticationControllerTest {

    private transient String authorization = "Authorization";
    private transient String applicationJson = "application/json";
    private transient String saved = "Saved";
    private transient String deleted = "Deleted";
    private transient String token = "token";

    private transient String defaultIntParam = "1";
    private transient String defaultString = "param";

    private final transient String adminRole = "role admin";

    private transient List<Object> objectList;
    private transient JSONArray jsonObjectList;

    @Autowired
    private transient MockMvc mockMvc;

    @MockBean
    private transient AuthenticationCommunicationService communicationService;

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
    public void whenValidInput_returnsAllUsers() throws Exception {
        when(communicationService.getAllUsers(any(String.class))).thenReturn(objectList);

        MvcResult mvcResult = mockMvc.perform(get("/gateway/allUsers")
                .contentType(applicationJson).header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonObjectList.toString(), res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_addsUser() throws Exception {
        when(communicationService.addUser(any(Integer.class), any(String.class),
                any(String.class), any(String.class), any(String.class), any(String.class),
                any(String.class))).thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/addUser")
                .contentType(applicationJson)
                .param("id", defaultIntParam)
                .param("firstName", defaultString)
                .param("lastName", defaultString)
                .param("password", defaultString)
                .param("role", defaultString)
                .param("netId", defaultString)
                .param("token", defaultString)
                .header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_deleteUser() throws Exception {
        when(communicationService.deleteUser(any(Integer.class), any(String.class)))
                .thenReturn(deleted);

        MvcResult mvcResult = mockMvc.perform(delete("/gateway/deleteUser")
                .contentType(applicationJson)
                .param("id", defaultIntParam)
                .header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(deleted, res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_updateUser() throws Exception {
        when(communicationService.updateUser(any(Integer.class), any(String.class),
                any(String.class), any(String.class), any(String.class), any(String.class),
                any(String.class))).thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(put("/gateway/updateUser")
                .contentType(applicationJson)
                .param("id", defaultIntParam)
                .param("firstName", defaultString)
                .param("lastName", defaultString)
                .param("password", defaultString)
                .param("role", defaultString)
                .param("netId", defaultString)
                .param("token", defaultString)
                .header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }

    @Test
    @WithMockUser(authorities = {adminRole})
    public void whenValidInput_returnsUserDetails() throws Exception {
        when(communicationService.getUserDetails(any(String.class), any(Integer.class)))
                .thenReturn(objectList);

        MvcResult mvcResult = mockMvc.perform(get("/gateway/admin/getUserDetails")
                .contentType(applicationJson)
                .param("id", defaultIntParam)
                .header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonObjectList.toString(), res);
    }

    @Test
    @WithMockUser(authorities = {adminRole})
    public void whenValidInput_addsAdmin() throws Exception {
        when(communicationService.addAdmin(any(String.class), any(Integer.class),
                any(String.class), any(String.class), any(String.class), any(String.class),
                any(String.class))).thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/admin/addAdmin")
                .contentType(applicationJson)
                .param("id", defaultIntParam)
                .param("firstName", defaultString)
                .param("lastName", defaultString)
                .param("password", defaultString)
                .param("role", defaultString)
                .param("netId", defaultString)
                .header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }

}
