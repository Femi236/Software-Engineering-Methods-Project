package nl.tudelft.sem.template.gateway.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.gateway.controllers.ReservationCommunicationController;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class ReservationIntegrationTest {

    private transient String jsonString;
    private transient List<String> expectedJsonArray;
    private transient JSONArray jsonArray;

    private transient String contentType = "Content-Type";
    private transient String applicationJson = "application/json";

    private transient String deleted = "Deleted";
    private transient String saved = "Saved";
    private transient String test = "test";

    private transient int defaultIntParam = 1;

    private transient String getUserIdPath = "/demo/getUserId?([a-z]*)";

    private transient WireMockServer authenticationWireMockServer;
    private transient WireMockServer reservationWireMockServer;
    private transient WireMockServer equipmentWireMockServer;

    /**
     * Before each method.
     */
    @BeforeEach
    public void init() {
        jsonString = "[ \"Test\", \"JSON\", \"Array\" ]";
        expectedJsonArray = new ArrayList<>();
        expectedJsonArray.add("Test");
        expectedJsonArray.add("JSON");
        expectedJsonArray.add("Array");
        jsonArray = new JSONArray(jsonString);
    }

    @Autowired
    private transient ReservationCommunicationController communicationController;

    @Test
    @WithMockUser
    public void whenCreateSingleFieldReservation_thenReturnSaved() {
        reservationWireMockServer = new WireMockServer(8085);
        reservationWireMockServer.start();

        authenticationWireMockServer = new WireMockServer(8080);
        authenticationWireMockServer.start();

        LocalDateTime dateTime = LocalDateTime.of(2015, Month.APRIL, 29, 11, 13, 40);

        authenticationWireMockServer.stubFor(get(urlPathMatching(getUserIdPath))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody("1")));

        reservationWireMockServer.stubFor(post(urlPathMatching("/fieldreservation/add?([a-z]*)"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody(saved)));

        String res = communicationController.singleFieldReservation("", 2, 5, dateTime);
        assertEquals(saved, res);

        authenticationWireMockServer.stop();
        reservationWireMockServer.stop();
    }

    @Test
    @WithMockUser
    public void whenCreateEquipmentReservation_thenReturnSaved() {
        reservationWireMockServer = new WireMockServer(8085);
        reservationWireMockServer.start();

        authenticationWireMockServer = new WireMockServer(8080);
        authenticationWireMockServer.start();

        LocalDateTime dateTime = LocalDateTime.of(2015, Month.APRIL, 29, 11, 13, 40);

        authenticationWireMockServer.stubFor(get(urlPathMatching(getUserIdPath))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody("1")));

        reservationWireMockServer
                .stubFor(post(urlPathMatching("/equipmentreservation/add?([a-z]*)"))
                        .willReturn(aResponse().withFixedDelay(1000)
                                .withStatus(200).withHeader(contentType, applicationJson)
                                .withBody(saved)));

        String res = communicationController.equipmentReservation("", 2, 5, dateTime);
        assertEquals(saved, res);

        authenticationWireMockServer.stop();
        reservationWireMockServer.stop();
    }

    @Test
    @WithMockUser
    public void whenCreateGroupFieldReservation_thenReturnSaved() {
        reservationWireMockServer = new WireMockServer(8085);
        reservationWireMockServer.start();

        authenticationWireMockServer = new WireMockServer(8080);
        authenticationWireMockServer.start();

        LocalDateTime dateTime = LocalDateTime.of(2015, Month.APRIL, 29, 11, 13, 40);

        authenticationWireMockServer.stubFor(get(urlPathMatching(getUserIdPath))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody("1")));

        reservationWireMockServer.stubFor(post(urlPathMatching("/fieldreservation/add?([a-z]*)"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody(saved)));

        String res = communicationController.groupFieldReservation("", 2, 5, dateTime, 5);
        assertEquals(saved, res);

        authenticationWireMockServer.stop();
        reservationWireMockServer.stop();
    }

    @Test
    @WithMockUser
    public void whenGetAllReservationsForEquipment_thenReturnsList() {
        reservationWireMockServer = new WireMockServer(8085);
        reservationWireMockServer.start();

        authenticationWireMockServer = new WireMockServer(8080);
        authenticationWireMockServer.start();

        authenticationWireMockServer.stubFor(get(urlPathMatching(getUserIdPath))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody("1")));

        JSONArray jsonArray = new JSONArray(jsonString);

        reservationWireMockServer
                .stubFor(post(urlPathMatching(
                        "/equipmentreservation/reservationsForEquipment?([a-z]*)"))
                        .willReturn(aResponse().withFixedDelay(1000)
                                .withStatus(200).withHeader(contentType, applicationJson)
                                .withBody(jsonArray.toString())));

        List<Object> res = communicationController.getEquipmentReservationsForEquipment(2);
        assertEquals(expectedJsonArray, res);

        authenticationWireMockServer.stop();
        reservationWireMockServer.stop();
    }

    @Test
    @WithMockUser
    public void whenDeleteEquipmentReservation_thenReturnDeleted() {
        equipmentWireMockServer = new WireMockServer(8085);
        equipmentWireMockServer.start();

        equipmentWireMockServer
                .stubFor(post(urlPathMatching("/equipmentreservation/delete?([a-z]*)"))
                        .willReturn(aResponse().withFixedDelay(1000)
                                .withStatus(200)
                                .withBody(deleted)));

        String res = communicationController.deleteEquipRes(defaultIntParam);
        assertEquals(deleted, res);

        equipmentWireMockServer.stop();
    }

    @Test
    @WithMockUser
    public void whenDeleteFieldReservation_thenReturnDeleted() {
        equipmentWireMockServer = new WireMockServer(8085);
        equipmentWireMockServer.start();

        equipmentWireMockServer.stubFor(post(urlPathMatching("/fieldreservation/delete?([a-z]*)"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200)
                        .withBody(deleted)));

        String res = communicationController.deleteFieldRes(defaultIntParam);
        assertEquals(deleted, res);

        equipmentWireMockServer.stop();
    }

    @Test
    @WithMockUser
    public void whenGetAllEquipmentReservations_thenReturnList() {
        equipmentWireMockServer = new WireMockServer(8085);
        equipmentWireMockServer.start();

        equipmentWireMockServer.stubFor(get(urlPathMatching("/equipmentreservation/all"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200)
                        .withBody(jsonArray.toString())));

        List<Object> res = communicationController.getAllEquipRes();
        assertEquals(expectedJsonArray, res);

        equipmentWireMockServer.stop();
    }

    @Test
    @WithMockUser
    public void whenGetAllFieldReservations_thenReturnList() {
        equipmentWireMockServer = new WireMockServer(8085);
        equipmentWireMockServer.start();

        equipmentWireMockServer.stubFor(get(urlPathMatching("/fieldreservation/all"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200)
                        .withBody(jsonArray.toString())));

        List<Object> res = communicationController.getAllFieldRes();
        assertEquals(expectedJsonArray, res);

        equipmentWireMockServer.stop();
    }
}
