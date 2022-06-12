package nl.tudelft.sem.template.equipment.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.equipment.entities.Equipment;
import nl.tudelft.sem.template.equipment.services.EquipmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = EquipmentController.class)
public class EquipmentControllerTest {
    private transient Equipment equipment1;
    private transient Equipment equipment2;
    private transient Equipment equipment3;

    private final transient String equipmentParam1 = "id";
    private final transient String equipmentParam2 = "type";

    private final transient String addEquipmentPath = "/equipment/add";
    private final transient String getEquipmentPath = "/equipment/all";
    private final transient String updateEquipmentPath = "/equipment/update";
    private final transient String deleteEquipmentPath = "/equipment/delete";

    private final transient String contentType = "application/json";

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    @MockBean
    private transient EquipmentService equipmentService;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        equipment1 = new Equipment(1, 10);
        equipment2 = new Equipment(2, 15);
        equipment3 = new Equipment(3, 5);
    }

    @Test
    public void whenValidInput_CapturesRightInput() throws Exception {

        mockMvc.perform(post(addEquipmentPath).contentType(contentType)
                .param(equipmentParam2, Integer.toString(equipment1.getType())))
                .andExpect(status().isOk());
        ArgumentCaptor<Integer> typeCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(equipmentService, times(1))
                .addNewEquipment(typeCaptor.capture());
        assertEquals(typeCaptor.getValue(), equipment1.getType());
    }

    @Test
    public void whenMissingType_thenReturnCorrectError() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(addEquipmentPath).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("type parameter is missing", res);
    }

    @Test
    public void whenWrongTypeType_thenReturnCorrectError() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(addEquipmentPath).contentType(contentType)
                .param(equipmentParam2, "zero"))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();
        assertEquals("Incorrect type for input: zero. Type should be: int", response);
    }

    @Test
    public void whenValidInput_thenReturnsSaved() throws Exception {
        when(equipmentService.addNewEquipment(any(Integer.class)))
                .thenReturn("Saved");
        MvcResult mvcResult = mockMvc.perform(post(addEquipmentPath).contentType(contentType)
                .param(equipmentParam2, Integer.toString(equipment1.getType())))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Saved", response);
    }

    @Test
    public void returnsRightList() throws Exception {
        List<Equipment> allEquipment = new ArrayList<>();
        allEquipment.add(equipment1);
        allEquipment.add(equipment2);
        allEquipment.add(equipment3);

        when(equipmentService.getAllEquipment()).thenReturn(allEquipment);

        MvcResult mvcResult = mockMvc.perform(get(getEquipmentPath)
                .contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(allEquipment))
                .isEqualToIgnoringWhitespace(response);
    }

    @Test
    public void whenValidInput_thenReturnsUpdated() throws Exception {
        when(equipmentService.updateEquipment(
                any(Integer.class), any(Integer.class)))
                .thenReturn("Updated");
        MvcResult mvcResult = mockMvc.perform(post(updateEquipmentPath).contentType(contentType)
                .param(equipmentParam1, Integer.toString(equipment1.getId()))
                .param(equipmentParam2, Integer.toString(equipment1.getType())))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Updated", response);
    }

    @Test
    public void whenValidInput_thenReturnsDeleted() throws Exception {
        when(equipmentService.deleteEquipment(any(Integer.class))).thenReturn("Deleted");
        MvcResult mvcResult = mockMvc.perform(delete(deleteEquipmentPath).contentType(contentType)
                .param(equipmentParam1, Integer.toString(equipment1.getId())))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Deleted", response);
    }


}
