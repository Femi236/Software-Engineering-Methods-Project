package nl.tudelft.sem.template.gateway.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.gateway.services.EquipmentCommunicationService;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@WebMvcTest(controllers = EquipmentCommunicationController.class)
public class EquipmentControllerTest {

    private transient String applicationJson = "application/json";
    private transient String saved = "Saved";

    private transient String defaultIntParam = "1";

    private final transient String adminRole = "role admin";

    private transient List<Object> objectList;
    private transient JSONArray jsonObjectList;

    @Autowired
    private transient MockMvc mockMvc;

    @MockBean
    private transient EquipmentCommunicationService communicationService;

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
    public void whenValidInput_addsEquipment() throws Exception {
        when(communicationService.addNewEquipment(any(Integer.class))).thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/addEquipment")
                .contentType(applicationJson)
                .param("type", defaultIntParam)).andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_returnsAllEquipment() throws Exception {
        when(communicationService.getAllEquipment()).thenReturn(objectList);

        MvcResult mvcResult = mockMvc.perform(get("/gateway/allEquipment")
                .contentType(applicationJson))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonObjectList.toString(), res);
    }

    @Test
    @WithMockUser(authorities = {adminRole})
    public void whenValidInput_addEquipment() throws Exception {
        when(communicationService.addEquipment(any(Integer.class))).thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/admin/equipment/addEquipment")
                .contentType(applicationJson)
                .param("type", defaultIntParam))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }
}
