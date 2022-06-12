package nl.tudelft.sem.template.reservations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.transaction.Transactional;
import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import nl.tudelft.sem.template.reservations.repositories.FieldReservationRepository;
import nl.tudelft.sem.template.reservations.services.FieldReservationService;
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
public class FieldReservationIntegrationTest {

    private transient FieldReservation er1;
    private transient FieldReservation er2;
    private transient FieldReservation er3;

    private transient String contentType = "/application/json";

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    FieldReservationService fieldResSer;

    @Autowired
    FieldReservationRepository fieldResRep;

    /**
     * Initializes the fieldReservation instances.
     */
    @BeforeEach
    public void beforeEach() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime time1 = LocalDateTime.parse("2030-12-12T12:12:12", formatter);
        LocalDateTime time2 = LocalDateTime.parse("2021-01-01T14:18:52", formatter);
        LocalDateTime time3 = LocalDateTime.parse("2024-04-09T09:30:30", formatter);
        er1 = new FieldReservation(9999, 9999, 9999, time1, 9999);
        er2 = new FieldReservation(9998, 9998, 9998, time2, 9998);
        er3 = new FieldReservation(9997, 9997, 9997, time3, 9997);
    }

    @Test
    public void addFieldReservationThroughAllLayers() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/fieldreservation/add")
                .contentType(contentType)
                .param("id", Integer.toString(er1.getId()))
                .param("fieldId", Integer.toString(er1.getFieldId()))
                .param("reserver", Integer.toString(er1.getReserver()))
                .param("startTime", er1.getStartTime().toString())
                .param("group", Integer.toString(90)))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Saved", res);
    }

    @Test
    public void deleteFieldReservationThroughAllLayers() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/fieldreservation/delete")
                .contentType(contentType)
                .param("id", Integer.toString(9998)))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Deleted FieldReservation", res);
    }

    @Test
    public void deleteFieldReservationThroughAllLayersFailed() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/fieldreservation/delete")
                .contentType(contentType)
                .param("id", Integer.toString(10000)))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("FAILED, FieldReservation does not exist", res);
    }

    @Test
    public void updateFieldReservationThroughAllLayers() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/fieldreservation/update")
                .contentType(contentType)
                .param("id", Integer.toString(9997))
                .param("fieldId", Integer.toString(er3.getFieldId()))
                .param("reserver", Integer.toString(er3.getReserver()))
                .param("startTime", er3.getStartTime().toString())
                .param("group", Integer.toString(er3.getGroup())))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("updated", res);

    }


    @Test
    public void updateFieldReservationThroughAllLayersFailed() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/fieldreservation/update")
                .contentType(contentType)
                .param("id", Integer.toString(10000))
                .param("fieldId", Integer.toString(er1.getFieldId()))
                .param("reserver", Integer.toString(er1.getReserver()))
                .param("startTime", er1.getStartTime().toString())
                .param("group", Integer.toString(er3.getGroup())))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("FieldReservation does not exist", res);

    }

}