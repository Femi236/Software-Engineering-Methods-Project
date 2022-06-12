package nl.tudelft.sem.template.equipment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import javax.transaction.Transactional;
import nl.tudelft.sem.template.equipment.entities.Equipment;
import nl.tudelft.sem.template.equipment.repositories.EquipmentRepository;
import nl.tudelft.sem.template.equipment.services.EquipmentService;
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
public class EquipmentIntegrationTest {

    private transient Equipment equipment1;
    private transient Equipment equipment2;
    private transient Equipment equipment3;

    private transient ArrayList<Equipment> equipmentList;

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    @Autowired
    private transient EquipmentService equipmentService;

    @Autowired
    private transient EquipmentRepository equipmentRepository;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        equipment1 = new Equipment(1, 5);
        equipment2 = new Equipment(2, 10);
        equipment3 = new Equipment(3, 15);

        equipmentRepository.save(equipment1);
        equipmentRepository.save(equipment2);
        equipmentRepository.save(equipment3);

        equipmentList = new ArrayList<>();
        equipmentList.add(equipment1);
        equipmentList.add(equipment2);
        equipmentList.add(equipment3);

    }

    @Test
    public void addEquipmentWorksThroughAllLayers() throws Exception {

        Equipment e = new Equipment(4, 200);
        equipmentRepository.save(e);

        MvcResult mvcResult = mockMvc.perform(post("/equipment/add")
                .contentType("application/json")
                .param("type", Integer.toString(e.getType())))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();

        assertEquals("Saved", res);

    }

    @Test
    public void updateEquipmentWorksThroughAllLayers() throws Exception {
        int newType = 0;

        MvcResult mvcResult = mockMvc.perform(post("/equipment/update")
                .contentType("application/json")
                .param("id", Integer.toString(equipment1.getId()))
                .param("type", Integer.toString(newType)))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Updated", res);
    }

    @Test
    public void deleteEquipmentWorksThroughAllLayers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/equipment/delete")
                .contentType("application/json")
                .param("id", Integer.toString(equipment1.getId())))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Deleted", res);
    }


}
