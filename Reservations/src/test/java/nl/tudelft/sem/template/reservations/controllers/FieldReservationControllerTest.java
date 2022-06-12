package nl.tudelft.sem.template.reservations.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.services.FieldReservationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = FieldReservationController.class)
public class FieldReservationControllerTest {

    private transient FieldReservation fr1;
    private transient FieldReservation fr2;
    private transient FieldReservation fr3;
    private transient FieldReservation fr1Field;
    private transient FieldReservation fr2Field;
    private transient FieldReservation fr1User;
    private transient FieldReservation fr2User;
    private transient FieldReservation fr3User;
    private transient List<FieldReservation> frList;
    private transient List<FieldReservation> frSameFieldList;
    private transient List<FieldReservation> frSameUserList;

    private transient Integer fieldId;
    private transient Integer userId;

    private transient String param1 = "id";
    private transient String param2 = "fieldId";
    private transient String param3 = "reserver";
    private transient String param4 =  "startTime";
    private transient String param5 = "group";



    private final transient String addReservation = "/fieldreservation/add";
    private final transient String getReservation = "/fieldreservation/all";
    private final transient String updateReservation = "/fieldreservation/update";
    private final transient String deleteReservation = "/fieldreservation/delete";
    private final transient String getResForFieldPath = "/fieldreservation/fieldsForField";
    private final transient String getResForUserPath = "/fieldreservation/userReservations";

    private final transient String contentType = "application/json";

    @Autowired
    private transient MockMvc mockMvc;


    private transient ObjectMapper om;

    @MockBean
    private transient FieldReservationService fieldReservationService;

    /**initialser.
     *
     */
    @BeforeEach
    public void beforeEach() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime time1 = LocalDateTime.parse("2030-12-12T12:12:12", formatter);
        LocalDateTime time2 = LocalDateTime.parse("2021-01-01T14:18:52", formatter);
        LocalDateTime time3 = LocalDateTime.parse("2024-04-09T09:30:30", formatter);

        fr1 = new FieldReservation(1, 35, 89, time1, 4);
        fr2 = new FieldReservation(2, 36, 90, time2, 5);
        fr3 = new FieldReservation(3, 37, 91, time3, 6);
        frList = new ArrayList<>();
        frList.add(fr1);
        frList.add(fr2);
        frList.add(fr3);

        fieldId = 37;
        fr1Field = new FieldReservation(4, 37, 89, time1, 4);
        fr2Field = new FieldReservation(5, 37, 90, time2, 5);
        frSameFieldList = new ArrayList<>();
        frSameFieldList.add(fr1Field);
        frSameFieldList.add(fr2Field);

        userId = 92;
        fr1User = new FieldReservation(6, 35, 92, time1, 4);
        fr2User = new FieldReservation(7, 36, 92, time2, 5);
        fr3User = new FieldReservation(8, 37, 92, time3, 6);
        frSameUserList = new ArrayList<>();
        frSameUserList.add(fr1User);
        frSameUserList.add(fr2User);
        frSameUserList.add(fr3User);
    }

    @Test
    public void whenValidInput_CapturesRightInput() throws Exception {
        MvcResult result  = mockMvc.perform(post(addReservation).contentType(contentType)
                .param(param1, Integer.toString(fr1.getId()))
                .param(param2, Integer.toString(fr1.getFieldId()))
                .param(param3, Integer.toString(fr1.getReserver()))
                .param(param4, "2030-12-12T12:12:12")
                .param(param5, Integer.toString(fr1.getGroup())))
                .andExpect(status().isOk()).andReturn();
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> fieldIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> reserverCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<LocalDateTime> dateCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<Integer> groupCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(fieldReservationService, times(1))
                .addNewFieldRes(idCaptor.capture(),
                        fieldIdCaptor.capture(), reserverCaptor.capture(),
                        dateCaptor.capture(), groupCaptor.capture());
        String res = result.getResponse().getContentAsString();
        assertEquals(idCaptor.getValue(), fr1.getId());
        assertEquals("Saved", res);
    }

    @Test
    public void updateFieldReservationTest() throws Exception {
        MvcResult result  = mockMvc.perform(post(updateReservation).contentType(contentType)
                .param(param1, Integer.toString(fr1.getId()))
                .param(param2, Integer.toString(fr1.getFieldId()))
                .param(param3, Integer.toString(fr1.getReserver()))
                .param(param4, "2030-12-12T12:12:12")
                .param(param5, Integer.toString(fr1.getGroup())))
                .andExpect(status().isOk()).andReturn();
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> fieldIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> reserverCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<LocalDateTime> dateCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        ArgumentCaptor<Integer> groupCaptor = ArgumentCaptor.forClass(Integer.class);

        verify(fieldReservationService, times(1))
                .updateFieldReservation(idCaptor.capture(), fieldIdCaptor.capture(),
                        reserverCaptor.capture(), dateCaptor.capture(), groupCaptor.capture());
        String res = result.getResponse().getContentAsString();
        assertEquals(idCaptor.getValue(), fr1.getId());
        assertEquals("", res);
    }

    @Test
    public void deleteFieldReservationTest() throws Exception {
        MvcResult result = mockMvc.perform(post(deleteReservation)
                .contentType(contentType) //Note:this was written as a postmapping
                .param(param1, Integer.toString(fr1.getId())))
                .andExpect(status().isOk()).andReturn();
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(fieldReservationService, times(1))
                .deleteFieldRes(idCaptor.capture());
        String res = result.getResponse().getContentAsString();
        assertEquals("", res);
    }

    @Test
    public void returnsRightListOfFieldRes() throws Exception {
        when(fieldReservationService.getAllFieldRes()).thenReturn(frList);

        this.mockMvc.perform(get(getReservation).contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(frList.size()))//.andReturn();
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[*].id", Matchers.containsInRelativeOrder(1, 2, 3)))
                .andExpect(jsonPath("$.[*].fieldId", Matchers.containsInRelativeOrder(35, 36, 37)))
                .andExpect(jsonPath("$.[*].reserver", Matchers.containsInRelativeOrder(89, 90, 91)))
                .andExpect(jsonPath("$.[*].startTime",
                        Matchers.containsInRelativeOrder(fr1.getStartTime().toString(),
                        fr2.getStartTime().toString(), fr3.getStartTime().toString())))
                .andExpect(jsonPath("$.[*].group", Matchers.containsInRelativeOrder(4, 5, 6)));
    }

    @Test
    public void returnsFieldResForCertainUser() throws Exception {
        when(fieldReservationService.userReservations(userId)).thenReturn(frSameUserList);

        this.mockMvc.perform(
                post(getResForUserPath)
                        .param("id", Integer.toString(userId))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(frSameUserList.size()))
                .andExpect(jsonPath("$.[*].id", Matchers.containsInRelativeOrder(6, 7, 8)))
                .andExpect(jsonPath("$.[*].fieldId", Matchers.containsInRelativeOrder(35, 36, 37)))
                .andExpect(jsonPath("$.[*].reserver", Matchers.containsInRelativeOrder(92, 92, 92)))
                .andExpect(jsonPath("$.[*].startTime", Matchers.containsInRelativeOrder(
                        fr1User.getStartTime().toString(),
                        fr2User.getStartTime().toString(), fr3User.getStartTime().toString())));

    }

}
