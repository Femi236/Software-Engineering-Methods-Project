package nl.tudelft.sem.template.gateway.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.gateway.services.FieldCommunicationService;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@WebMvcTest(controllers = FieldCommunicationController.class)
public class FieldControllerTest {

    private transient String applicationJson = "application/json";
    private transient String saved = "Saved";

    private transient String defaultIntParam = "1";
    private transient String defaultString = "param";

    private final transient String adminRole = "role admin";

    private transient List<Object> objectList;
    private transient JSONArray jsonObjectList;

    @Autowired
    private transient MockMvc mockMvc;

    @MockBean
    private transient FieldCommunicationService communicationService;

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
    @WithMockUser(authorities = {adminRole})
    public void whenValidInput_addField() throws Exception {
        when(communicationService.addField(any(Integer.class),
                any(String.class), any(Integer.class),
                any(Integer.class))).thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/admin/field/addField")
                .contentType(applicationJson)
                .param("id", defaultIntParam)
                .param("name", defaultString)
                .param("minCapacity", defaultIntParam)
                .param("maxCapacity", defaultIntParam))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }

    @Test
    @WithMockUser(authorities = {adminRole})
    public void whenValidInput_changeCapacity() throws Exception {
        when(communicationService.changeCapacity(any(Integer.class), any(Integer.class)))
                .thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/admin/field/changeCapacity")
                .contentType(applicationJson)
                .param("id", defaultIntParam)
                .param("maxCapacity", defaultIntParam))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }
}
