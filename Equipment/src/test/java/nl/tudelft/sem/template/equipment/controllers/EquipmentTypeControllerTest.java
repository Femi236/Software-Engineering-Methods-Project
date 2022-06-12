package nl.tudelft.sem.template.equipment.controllers;

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
import nl.tudelft.sem.template.equipment.entities.EquipmentType;
import nl.tudelft.sem.template.equipment.services.EquipmentTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@WebMvcTest(controllers = EquipmentTypeController.class)
public class EquipmentTypeControllerTest {
    private transient EquipmentType equipmentType1;
    private transient EquipmentType equipmentType2;
    private transient EquipmentType equipmentType3;

    private final transient String equipmentTypeParam1 = "id";
    private final transient String equipmentTypeParam2 = "name";

    private final transient String addEquipmentTypePath = "/equipmentType/add";
    private final transient String getEquipmentTypePath = "/equipmentType/all";
    private final transient String updateEquipmentTypePath = "/equipmentType/update";
    private final transient String deleteEquipmentTypePath = "/equipmentType/delete";

    private final transient String contentType = "application/json";

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    @MockBean
    private transient EquipmentTypeService equipmentTypeService;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        equipmentType1 = new EquipmentType(1, "ball");
        equipmentType2 = new EquipmentType(2, "bat");
        equipmentType3 = new EquipmentType(3, "net");
    }

    @Test
    public void whenValidInput_CapturesRightInput() throws Exception {

        mockMvc.perform(post(addEquipmentTypePath).contentType(contentType)
                .param(equipmentTypeParam2, equipmentType1.getName()))
                .andExpect(status().isOk());
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        verify(equipmentTypeService, times(1))
                .addNewEquipmentType(nameCaptor.capture());
        assertEquals(nameCaptor.getValue(), equipmentType1.getName());
    }

    @Test
    public void whenMissingName_thenReturnCorrectError() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(addEquipmentTypePath).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("name parameter is missing", res);
    }

    @Test
    public void whenValidInput_thenReturnsSaved() throws Exception {
        when(equipmentTypeService.addNewEquipmentType(any(String.class)))
                .thenReturn("Saved");
        MvcResult mvcResult = mockMvc.perform(post(addEquipmentTypePath).contentType(contentType)
                .param(equipmentTypeParam2, equipmentType1.getName()))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Saved", response);
    }

    @Test
    public void returnsRightList() throws Exception {
        List<EquipmentType> allEquipmentType = new ArrayList<>();
        allEquipmentType.add(equipmentType1);
        allEquipmentType.add(equipmentType2);
        allEquipmentType.add(equipmentType3);

        when(equipmentTypeService.getAllEquipmentType()).thenReturn(allEquipmentType);

        MvcResult mvcResult = mockMvc.perform(get(getEquipmentTypePath)
                .contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(allEquipmentType))
                .isEqualToIgnoringWhitespace(response);
    }

    @Test
    public void whenValidInput_thenReturnsUpdated() throws Exception {
        when(equipmentTypeService.updateEquipmentType(
                any(Integer.class), any(String.class)))
                .thenReturn("Updated");
        MvcResult mvcResult = mockMvc.perform(post(updateEquipmentTypePath).contentType(contentType)
                .param(equipmentTypeParam1, Integer.toString(equipmentType1.getId()))
                .param(equipmentTypeParam2, equipmentType1.getName()))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Updated", response);
    }

    @Test
    public void whenValidInput_thenReturnsDeleted() throws Exception {
        when(equipmentTypeService.deleteEquipmentType(any(Integer.class))).thenReturn("Deleted");
        MvcResult mvcResult = mockMvc.perform(post(deleteEquipmentTypePath).contentType(contentType)
                .param(equipmentTypeParam1, Integer.toString(equipmentType1.getId())))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Deleted", response);
    }

}