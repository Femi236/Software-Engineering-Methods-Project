package nl.tudelft.sem.template.groups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import nl.tudelft.sem.template.groups.entities.Group;
import nl.tudelft.sem.template.groups.repositories.GroupRepository;
import nl.tudelft.sem.template.groups.services.GroupService;
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
public class GroupIntegrationTest {

    private transient Group group1;
    private transient Group group2;
    private transient Group group3;

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    @Autowired
    private transient GroupService groupService;

    @Autowired
    private transient GroupRepository groupRepository;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        group1 = new Group(1, 3);
        group2 = new Group(2, 4);
        group3 = new Group(3, 5);
    }

    @Test
    public void addGroupWorksThroughAllLayers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/group/add").contentType("application/json")
                .param("creator", Integer.toString(group1.getCreator())))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();

        assertEquals("Saved", res);
    }

    @Test
    public void updateGroupWorksThroughAllLayers() throws Exception {
        groupRepository.save(group1);
        MvcResult mvcResult = mockMvc.perform(post("/group/update").contentType("application/json")
                .param("id", Integer.toString(group1.getId()))
                .param("creator", Integer.toString(group2.getCreator())))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();

        assertEquals("Updated", res);
    }

    @Test
    public void deleteGroupWorksThroughAllLayers() throws Exception {
        groupRepository.save(group1);
        MvcResult mvcResult = mockMvc.perform(post("/group/delete").contentType("application/json")
                .param("id", Integer.toString(group1.getId())))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();

        assertEquals("Deleted", res);
    }

}
