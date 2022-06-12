package nl.tudelft.sem.template.field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


@WebMvcTest(controllers = FieldController.class)
public class FieldControllerTest {

    private transient Field field1;
    private transient Field field2;

    private final transient String fieldParamId = "id";
    private final transient String fieldParamName = "name";
    private final transient String fieldParamMinCap = "minCapacity";
    private final transient String fieldParamMaxCap = "maxCapacity";

    private final transient String addFieldPath = "/field/add";
    private final transient String getFieldPath = "/field/all";
    private final transient String updateFieldPath = "/field/update";
    private final transient String deleteFieldPath = "/field/delete";

    private final transient String contentType = "application/json";

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    @MockBean
    private transient FieldService fieldService;

    @BeforeEach
    public void init() {
        field1 = new Field(1, "tennis court", 2, 4);
        field2 = new Field(2, "dance studio", 1, 6);
    }

    // addNewField tests
    @Test
    public void whenValidInput_CapturesRightInput() throws Exception {

        mockMvc.perform(post(addFieldPath).contentType(contentType)
                .param(fieldParamId, Integer.toString(field1.getId()))
                .param(fieldParamName, field1.getName())
                .param(fieldParamMinCap, Integer.toString(field1.getMin_capacity()))
                .param(fieldParamMaxCap, Integer.toString(field1.getMax_capacity())))
                .andExpect(status().isOk());
        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Integer> minCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Integer> maxCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(fieldService, times(1)).addNewField(idCaptor.capture(),
                nameCaptor.capture(),
                minCaptor.capture(),
                maxCaptor.capture());
        assertEquals(nameCaptor.getValue(), field1.getName());
    }

    @Test
    public void whenValidInput_thenReturnsSaved() throws Exception {
        when(fieldService.addNewField(any(Integer.class), any(String.class),
                any(Integer.class), any(Integer.class)))
                .thenReturn("Saved");
        MvcResult mvcResult = mockMvc.perform(post(addFieldPath).contentType(contentType)
                .param(fieldParamId, Integer.toString(field1.getId()))
                .param(fieldParamName, field1.getName())
                .param(fieldParamMinCap, Integer.toString(field1.getMin_capacity()))
                .param(fieldParamMaxCap, Integer.toString(field1.getMax_capacity())))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Saved", response);
    }

    @Test
    public void whenMissingName_thenReturnCorrectError() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post(addFieldPath).contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("id parameter is missing", res);
    }

    // getAllFields tests
    @Test
    public void returnsRightList() throws Exception {
        List<Field> allFields = new ArrayList<>();
        allFields.add(field1);
        allFields.add(field2);

        when(fieldService.getAllFields()).thenReturn(allFields);

        MvcResult mvcResult = mockMvc.perform(get(getFieldPath)
                .contentType(contentType))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertThat(objectMapper.writeValueAsString(allFields))
                .isEqualToIgnoringWhitespace(response);
    }

    // updateField tests
    @Test
    public void whenValidInput_thenReturnsUpdated() throws Exception {
        when(fieldService.updateField(any(Integer.class), any(String.class),
                any(Integer.class), any(Integer.class)))
                .thenReturn("Updated");
        MvcResult mvcResult = mockMvc.perform(post(updateFieldPath).contentType(contentType)
                .param(fieldParamId, Integer.toString(field1.getId()))
                .param(fieldParamName, field1.getName())
                .param(fieldParamMinCap, Integer.toString(field1.getMin_capacity()))
                .param(fieldParamMaxCap, Integer.toString(field1.getMax_capacity())))
                .andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Updated", response);
    }

    // deleteField tests
    @Test
    public void whenValidInput_thenReturnsDeleted() throws Exception {
        when(fieldService.deleteField(any(Integer.class))).thenReturn("Deleted");
        MvcResult mvcResult = mockMvc.perform(post(deleteFieldPath).contentType(contentType)
                .param(fieldParamId, Integer.toString(field1.getId()))
                .param(fieldParamName, field1.getName())
                .param(fieldParamMinCap, Integer.toString(field1.getMin_capacity()))
                .param(fieldParamMaxCap, Integer.toString(field1.getMax_capacity())))
                .andExpect(status().isOk()).andReturn();

        String response = mvcResult.getResponse().getContentAsString();

        assertEquals("Deleted", response);
    }

}
