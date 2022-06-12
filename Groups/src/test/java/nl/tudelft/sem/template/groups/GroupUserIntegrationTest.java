package nl.tudelft.sem.template.groups;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.transaction.Transactional;
import nl.tudelft.sem.template.groups.entities.GroupUser;
import nl.tudelft.sem.template.groups.repositories.GroupUserRepository;
import nl.tudelft.sem.template.groups.services.GroupUserService;
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
public class GroupUserIntegrationTest {
    private transient GroupUser groupUser1;

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    private transient ObjectMapper objectMapper;

    @Autowired
    private transient GroupUserService groupUserService;

    @Autowired
    private transient GroupUserRepository groupUserRepository;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        groupUser1 = new GroupUser(1, 1);
    }

    @Test
    public void addGroupUserWorksThroughAllLayers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/groupUser/add")
                .contentType("application/json")
                .param("group", Integer.toString(groupUser1.getGroup()))
                .param("user", Integer.toString(groupUser1.getUser())))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();

        assertEquals("Saved", res);
    }

    @Test
    public void deleteGroupUserWorksThroughAllLayers() throws Exception {
        groupUserRepository.save(groupUser1);
        MvcResult mvcResult = mockMvc.perform(post("/groupUser/delete")
                .contentType("application/json")
                .param("group", Integer.toString(groupUser1.getGroup()))
                .param("user", Integer.toString(groupUser1.getUser())))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();

        assertEquals("Deleted", res);
    }
}
