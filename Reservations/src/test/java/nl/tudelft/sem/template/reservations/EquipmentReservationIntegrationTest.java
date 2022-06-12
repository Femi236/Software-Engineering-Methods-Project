package nl.tudelft.sem.template.reservations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.transaction.Transactional;
import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import nl.tudelft.sem.template.reservations.repositories.EquipmentReservationRepository;
import nl.tudelft.sem.template.reservations.services.EquipmentReservationService;
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
public class EquipmentReservationIntegrationTest {

    private transient EquipmentReservation er1;
    private transient EquipmentReservation er2;
    private transient EquipmentReservation er3;

    private transient String contentType = "/application/json";

    private transient ArrayList<EquipmentReservation> list = new ArrayList<>();

    @Autowired
    private transient MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EquipmentReservationService equipResSer;

    @Autowired
    EquipmentReservationRepository equipResRep;

    /**
     * Initializes the equipmentReservation instances.
     */
    @BeforeEach
    public void beforeEach() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        LocalDateTime time1 = LocalDateTime.parse("2030-12-12T12:12:12", formatter);
        LocalDateTime time2 = LocalDateTime.parse("2021-01-01T14:18:52", formatter);
        LocalDateTime time3 = LocalDateTime.parse("2024-04-09T09:30:30", formatter);
        er1 = new EquipmentReservation(9999, 9999, 9999, time1);
        er2 = new EquipmentReservation(50, 9998, 9998, time2);
        er3 = new EquipmentReservation(51, 9997, 9997, time3);
    }

    @Test
    public void addEquipmentReservationThroughAllLayers() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/equipmentreservation/add")
            .contentType(contentType)
            .param("id", Integer.toString(er1.getId()))
            .param("equipmentId", Integer.toString(er1.getEquipmentId()))
            .param("reserver", Integer.toString(er1.getReserver()))
            .param("startTime", er1.getStartTime().toString()))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Saved", res);
    }

    @Test
    public void deleteEquipmentReservationThroughAllLayers() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/equipmentreservation/delete")
                .contentType(contentType)
                .param("id", Integer.toString(er2.getId())))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("Deleted EquipmentReservation", res);
    }

    @Test
    public void deleteEquipmentReservationThroughAllLayersFailed() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/equipmentreservation/delete")
                .contentType(contentType)
                .param("id", Integer.toString(9998)))
                .andExpect(status().isOk()).andReturn();

        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("FAILED, EquipmentReservation does not exist", res);
    }

    @Test
    public void updateEquipmentReservationThroughAllLayers() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/equipmentreservation/update")
                .contentType(contentType)
                .param("id", Integer.toString(er3.getId()))
                .param("equipmentId", Integer.toString(er1.getEquipmentId()))
                .param("reserver", Integer.toString(er1.getReserver()))
                .param("startTime", er1.getStartTime().toString()))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("updated", res);

    }


    @Test
    public void updateEquipmentReservationThroughAllLayersFailed() throws Exception {

        MvcResult mvcResult = mockMvc.perform(post("/equipmentreservation/update")
                .contentType(contentType)
                .param("id", Integer.toString(9999))
                .param("equipmentId", Integer.toString(er1.getEquipmentId()))
                .param("reserver", Integer.toString(er1.getReserver()))
                .param("startTime", er1.getStartTime().toString()))
                .andExpect(status().isOk()).andReturn();
        String res = mvcResult.getResponse().getContentAsString();
        assertEquals("EquipmentReservation does not exist", res);

    }


}
