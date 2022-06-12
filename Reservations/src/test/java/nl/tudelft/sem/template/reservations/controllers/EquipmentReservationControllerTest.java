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
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.services.EquipmentReservationService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;




@WebMvcTest(controllers = EquipmentReservationController.class)
public class EquipmentReservationControllerTest {

    private transient EquipmentReservation er1;
    private transient EquipmentReservation er2;
    private transient EquipmentReservation er3;
    private transient EquipmentReservation er1Equip;
    private transient EquipmentReservation er2Equip;
    private transient EquipmentReservation er3Equip;
    private transient EquipmentReservation er4Equip;
    private transient EquipmentReservation er1User;
    private transient EquipmentReservation er2User;
    private transient EquipmentReservation er3User;
    private transient List<EquipmentReservation> erList;
    private transient List<EquipmentReservation> erSameEquipList;
    private transient List<EquipmentReservation> erSameUserList;

    private transient Integer equipId;
    private transient Integer userId;

    private transient String param1 = "id";
    private transient String param2 = "equipmentId";
    private transient String param3 = "reserver";
    private transient String param4 =  "startTime";



    private final transient String addReservation = "/equipmentreservation/add";
    private final transient String getReservation = "/equipmentreservation/all";
    private final transient String updateReservation = "/equipmentreservation/update";
    private final transient String deleteReservation = "/equipmentreservation/delete";
    private final transient String getResForEquipPath = "/equipmentreservation"
            + "/reservationsForEquipment";
    private final transient String getResForUserPath = "/equipmentreservation/userReservations";


    private final transient String contentType = "application/json";



    @Autowired
    private transient MockMvc mockMvc;


    private transient ObjectMapper om;

    @MockBean
    private transient EquipmentReservationService equipmentReservationService;


    /**initialser.
     *
     */
    @BeforeEach
    public void beforeEach() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime time1 = LocalDateTime.parse("2030-12-12T12:12:12", formatter);
        LocalDateTime time2 = LocalDateTime.parse("2021-01-01T14:18:52", formatter);
        LocalDateTime time3 = LocalDateTime.parse("2024-04-09T09:30:30", formatter);
        er1 = new EquipmentReservation(1, 35, 89, time1);
        er2 = new EquipmentReservation(2, 45, 9, time2);
        er3 = new EquipmentReservation(3, 20, 7, time3);
        erList = new ArrayList<>();
        erList.add(er1);
        erList.add(er2);
        erList.add(er3);

        equipId = 5;
        er1Equip = new EquipmentReservation(4, 5, 10, time1);
        er2Equip = new EquipmentReservation(5, 5, 11, time2);
        er3Equip = new EquipmentReservation(6, 5, 12, time3);
        LocalDateTime time4 = LocalDateTime.parse("2025-04-11T19:25:35", formatter);
        er4Equip = new EquipmentReservation(7, 5, 13, time4);
        erSameEquipList = new ArrayList<>();
        erSameEquipList.add(er1Equip);
        erSameEquipList.add(er2Equip);
        erSameEquipList.add(er3Equip);
        erSameEquipList.add(er4Equip);

        userId = 10;
        er1User = new EquipmentReservation(4, 5, 10, time1);
        er2User = new EquipmentReservation(5, 6, 10, time2);
        er3User = new EquipmentReservation(6, 7, 10, time3);
        erSameUserList = new ArrayList<>();
        erSameUserList.add(er1User);
        erSameUserList.add(er2User);
        erSameUserList.add(er3User);
    }

    @Test
    public void whenValidInput_CapturesRightInput() throws Exception {
        MvcResult result  = mockMvc.perform(post(addReservation).contentType(contentType)
                .param(param1, Integer.toString(er1.getId()))
                .param(param2, Integer.toString(er1.getEquipmentId()))
                .param(param3, Integer.toString(er1.getReserver()))
                .param(param4, "2030-12-12T12:12:12"))
                .andExpect(status().isOk()).andReturn();
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> equipmentIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> reserverCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<LocalDateTime> dateCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(equipmentReservationService, times(1))
                .addNewEquipmentRes(idCaptor.capture(), equipmentIdCaptor.capture(),
                        reserverCaptor.capture(), dateCaptor.capture());
        String res = result.getResponse().getContentAsString();
        assertEquals(idCaptor.getValue(), er1.getId());
        assertEquals("Saved", res);
    }

    @Test
    public void deleteEquipmentReservationTest() throws Exception {
        MvcResult result = mockMvc.perform(post(deleteReservation)
                .contentType(contentType) //Note:this was written as a postmapping
                .param(param1, Integer.toString(er1.getId())))
                .andExpect(status().isOk()).andReturn();
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(equipmentReservationService, times(1))
                .deleteEquipmentRes(idCaptor.capture());
        String res = result.getResponse().getContentAsString();
        assertEquals("", res);
    }

    @Test
    public void updateEquipmentReservationTest() throws Exception {
        MvcResult result  = mockMvc.perform(post(updateReservation).contentType(contentType)
                .param(param1, Integer.toString(er1.getId()))
                .param(param2, Integer.toString(er1.getEquipmentId()))
                .param(param3, Integer.toString(er1.getReserver()))
                .param(param4, "2030-12-12T12:12:12"))
                .andExpect(status().isOk()).andReturn();
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> equipmentIdCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> reserverCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<LocalDateTime> dateCaptor = ArgumentCaptor.forClass(LocalDateTime.class);
        verify(equipmentReservationService, times(1))
                .updateEquipmentReservation(idCaptor.capture(), equipmentIdCaptor.capture(),
                        reserverCaptor.capture(), dateCaptor.capture());
        String res = result.getResponse().getContentAsString();
        assertEquals(idCaptor.getValue(), er1.getId());
        assertEquals("", res);
    }

    @Test
    public void returnsRightListOfEquipRes() throws Exception {
        when(equipmentReservationService.getAllEquipmentRes()).thenReturn(erList);

        this.mockMvc.perform(get(getReservation).contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(erList.size()))//.andReturn();
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.[0].id", Matchers.is(1)))
                .andExpect(jsonPath("$.[*].id", Matchers.containsInRelativeOrder(1, 2, 3)))
                .andExpect(jsonPath("$.[*].equipmentId",
                        Matchers.containsInRelativeOrder(35, 45, 20)))
                .andExpect(jsonPath("$.[*].reserver", Matchers.containsInRelativeOrder(89, 9, 7)))
                .andExpect(jsonPath("$.[*].startTime",
                        Matchers.containsInRelativeOrder(er1.getStartTime().toString(),
                        er2.getStartTime().toString(), er3.getStartTime().toString())));
    }

    @Test
    public void returnsEquipResForCertainEquip() throws Exception {
        when(equipmentReservationService.getReservationsForEquipment(equipId))
                .thenReturn(erSameEquipList);

        this.mockMvc.perform(
                post(getResForEquipPath)
                        .contentType(contentType)
                        .param("id", Integer.toString(equipId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(erSameEquipList.size()))
                .andExpect(jsonPath("$.[*].id", Matchers.containsInRelativeOrder(4, 5, 6, 7)))
                .andExpect(jsonPath("$.[*].equipmentId",
                        Matchers.containsInRelativeOrder(5, 5, 5, 5)))
                .andExpect(jsonPath("$.[*].reserver",
                        Matchers.containsInRelativeOrder(10, 11, 12, 13)))
                .andExpect(jsonPath("$.[*].startTime", Matchers.containsInRelativeOrder(
                        er1Equip.getStartTime().toString(), er2Equip.getStartTime().toString(),
                        er3Equip.getStartTime().toString(), er4Equip.getStartTime().toString())));
    }

    @Test
    public void returnsEquipResForCertainUser() throws Exception {
        when(equipmentReservationService.userReservations(userId)).thenReturn(erSameUserList);

        this.mockMvc.perform(
                post(getResForUserPath)
                        .param("id", Integer.toString(userId))
                        .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.size()").value(erSameUserList.size()))
                .andExpect(jsonPath("$.[*].id", Matchers.containsInRelativeOrder(4, 5, 6)))
                .andExpect(jsonPath("$.[*].equipmentId", Matchers.containsInRelativeOrder(5, 6, 7)))
                .andExpect(jsonPath("$.[*].reserver", Matchers.containsInRelativeOrder(10, 10, 10)))
                .andExpect(jsonPath("$.[*].startTime", Matchers.containsInRelativeOrder(
                        er1User.getStartTime().toString(),
                        er2User.getStartTime().toString(), er3User.getStartTime().toString())));

    }

}
