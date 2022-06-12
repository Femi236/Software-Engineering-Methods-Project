package nl.tudelft.sem.template.equipment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import javax.transaction.Transactional;
import nl.tudelft.sem.template.equipment.entities.EquipmentType;
import nl.tudelft.sem.template.equipment.repositories.EquipmentTypeRepository;
import nl.tudelft.sem.template.equipment.services.EquipmentTypeService;
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
public class EquipmentTypeIntegrationTest {

    private transient EquipmentType equipmentType1;
    private transient EquipmentType equipmentType2;
    private transient EquipmentType equipmentType3;

    private transient ArrayList<EquipmentType> equipmentTypeList;

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    @Autowired
    private transient EquipmentTypeService equipmentTypeService;

    @Autowired
    private transient EquipmentTypeRepository equipmentTypeRepository;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        equipmentType1 = new EquipmentType(1, "ball");
        equipmentType2 = new EquipmentType(2, "bat");
        equipmentType3 = new EquipmentType(3, "net");

        equipmentTypeRepository.save(equipmentType1);
        equipmentTypeRepository.save(equipmentType2);
        equipmentTypeRepository.save(equipmentType3);

        equipmentTypeList = new ArrayList<>();
        equipmentTypeList.add(equipmentType1);
        equipmentTypeList.add(equipmentType2);
        equipmentTypeList.add(equipmentType3);



    }

    @Test
    public void addEquipmentTypeWorksThroughAllLayers() throws Exception {

        EquipmentType e = new EquipmentType(4, "gloves");

        MvcResult mvcResult = mockMvc.perform(post("/equipmentType/add")
                .contentType("application/json")
                .param("name", e.getName()))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();

        assertEquals("Saved", res);

    }

    @Test
    public void updateEquipmentTypeWorksThroughAllLayers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/equipmentType/update")
                .contentType("application/json")
                .param("id", Integer.toString(equipmentType1.getId()))
                .param("name", equipmentType2.getName()))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Updated", res);
    }

    @Test
    public void deleteEquipmentTypeWorksThroughAllLayers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/equipmentType/delete")
                .contentType("application/json")
                .param("id", Integer.toString(equipmentType1.getId())))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Deleted", res);
    }


}