package nl.tudelft.sem.template.field;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Transactional
@AutoConfigureMockMvc
public class FieldIntegrationTest {

    private transient String applicationJson = "application/json";

    private transient Field field1;
    private transient Field field2;
    private transient List<Field> fieldList;

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    @Autowired
    private transient FieldService fieldService;

    @Autowired
    private transient FieldRepository fieldRepository;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        field1 = new Field(1, "tennis court", 2, 4);
        field2 = new Field(2, "dance studio", 1, 6);
        fieldList = new ArrayList<>();

        fieldList.add(field1);
        fieldList.add(field2);

        fieldRepository.save(field1);
        fieldRepository.save(field2);
    }

    @Test
    public void addFieldWorksThroughAllLayers() throws Exception {

        Field f = new Field(1, "tennis court", 2, 4);
        fieldRepository.save(f);

        MvcResult mvcResult = mockMvc.perform(post("/field/add")
                .contentType(applicationJson)
                .param("id", Integer.toString(f.getId()))
                .param("name", f.getName())
                .param("minCapacity", Integer.toString(f.getMin_capacity()))
                .param("maxCapacity", Integer.toString(f.getMax_capacity())))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();

        assertEquals("Saved", res);

    }

    @Test
    public void updateFieldWorksThroughAllLayers() throws Exception {
        String newName = "Basketball field";

        MvcResult mvcResult = mockMvc.perform(post("/field/update")
                .contentType(applicationJson)
                .param("id", Integer.toString(field1.getId()))
                .param("name", newName)
                .param("minCapacity", Integer.toString(field1.getMin_capacity()))
                .param("maxCapacity", Integer.toString(field1.getMax_capacity())))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Updated", res);
    }

    @Test
    public void deleteFieldWorksThroughAllLayers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/field/delete")
                .contentType(applicationJson)
                .param("id", Integer.toString(field1.getId())))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Deleted", res);
    }

    @Test
    public void changeCapacityWorksThroughAllLayers() throws Exception {
        int newMaxCapacity = 10;

        MvcResult mvcResult = mockMvc.perform(post("/field/changeCapacity")
                .contentType(applicationJson)
                .param("id", Integer.toString(field1.getId()))
                .param("maxCapacity", Integer.toString(newMaxCapacity)))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Maximum capacity updated", res);
    }


}
