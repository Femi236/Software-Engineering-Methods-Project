package nl.tudelft.sem.template.gateway.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.gateway.services.ReservationCommunicationService;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@WebMvcTest(controllers = ReservationCommunicationController.class)
public class ReservationControllerTest {

    private transient String authorization = "Authorization";
    private transient String applicationJson = "application/json";
    private transient String saved = "Saved";
    private transient String deleted = "Deleted";
    private transient String token = "token";

    private transient String defaultIntParam = "1";
    private transient String defaultString = "param";
    private transient String defaultDateTimeParam = "2020-12-11T12:20:23";

    private transient String group = "group";
    private final transient String adminRole = "role admin";

    private transient List<Object> objectList;
    private transient JSONArray jsonObjectList;

    @Autowired
    private transient MockMvc mockMvc;

    @MockBean
    private transient ReservationCommunicationService communicationService;

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
    public void whenValidInput_returnsAllEquipmentReservations() throws Exception {
        when(communicationService.getEquipmentReservations(any(String.class)))
                .thenReturn(objectList);

        MvcResult mvcResult = mockMvc.perform(get("/gateway/getEquipmentReservations")
                .contentType(applicationJson).header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonObjectList.toString(), res);
    }

    @Test
    @WithMockUser(authorities = {adminRole})
    public void whenValidInput_returnsEquipmentReservationsForEquipment() throws Exception {
        when(communicationService.getEquipmentReservationsForEquipment(any(Integer.class)))
                .thenReturn(objectList);

        MvcResult mvcResult = mockMvc
                .perform(get("/gateway/admin/getEquipmentReservationsForEquipment")
                        .contentType(applicationJson)
                        .param("id", defaultIntParam))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonObjectList.toString(), res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_createsSingleFieldReservation() throws Exception {
        when(communicationService.singleFieldReservation(any(String.class), any(Integer.class),
                any(Integer.class), any(LocalDateTime.class))).thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/singleFieldReservation")
                .contentType(applicationJson)
                .param("id", defaultIntParam)
                .param("fieldId", defaultIntParam)
                .param("startTime", defaultDateTimeParam)
                .header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_createsGroupFieldReservation() throws Exception {
        when(communicationService.groupFieldReservation(any(String.class), any(Integer.class),
                any(Integer.class), any(LocalDateTime.class), any(Integer.class)))
                .thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/groupFieldReservation")
                .contentType(applicationJson)
                .param("id", defaultIntParam)
                .param("fieldId", defaultIntParam)
                .param("startTime", defaultDateTimeParam)
                .param(group, defaultIntParam)
                .header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_createsEquipmentReservation() throws Exception {
        when(communicationService.equipmentReservation(any(String.class), any(Integer.class),
                any(Integer.class), any(LocalDateTime.class))).thenReturn(saved);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/equipmentReservation")
                .contentType(applicationJson)
                .param("id", defaultIntParam)
                .param("equipmentId", defaultIntParam)
                .param("startTime", defaultDateTimeParam)
                .header(authorization, token))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(saved, res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_deleteEquipmentReservation() throws Exception {
        when(communicationService.deleteEquipRes(any(Integer.class))).thenReturn(deleted);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/deleteEquipmentReservation")
                .contentType(applicationJson)
                .param("id", defaultIntParam))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(deleted, res);
    }

    @Test
    @WithMockUser
    public void whenValidInput_deleteFieldReservation() throws Exception {
        when(communicationService.deleteFieldRes(any(Integer.class))).thenReturn(deleted);

        MvcResult mvcResult = mockMvc.perform(post("/gateway/deleteFieldReservation")
                .contentType(applicationJson)
                .param("id", defaultIntParam))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals(deleted, res);
    }

    @Test
    @WithMockUser(authorities = {adminRole})
    public void whenValidInput_returnsAllEquipmentReservationsAdmin() throws Exception {
        when(communicationService.getAllEquipRes()).thenReturn(objectList);

        MvcResult mvcResult = mockMvc
                .perform(get("/gateway/admin/allEquipmentReservations")
                        .contentType(applicationJson))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonObjectList.toString(), res);
    }

    @Test
    @WithMockUser(authorities = {adminRole})
    public void whenValidInput_returnsAllFieldReservationsAdmin() throws Exception {
        when(communicationService.getAllFieldRes()).thenReturn(objectList);

        MvcResult mvcResult = mockMvc.perform(get("/gateway/admin/allFieldReservations")
                .contentType(applicationJson))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();

        assertEquals(jsonObjectList.toString(), res);
    }

}
