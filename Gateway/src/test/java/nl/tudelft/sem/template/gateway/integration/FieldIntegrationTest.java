package nl.tudelft.sem.template.gateway.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.gateway.controllers.FieldCommunicationController;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class FieldIntegrationTest {

    private transient String jsonString;
    private transient List<String> expectedJsonArray;
    private transient JSONArray jsonArray;

    private transient String contentType = "Content-Type";
    private transient String applicationJson = "application/json";

    private transient String deleted = "Deleted";

    private transient String getUserIdPath = "/demo/getUserId?([a-z]*)";

    private transient WireMockServer authenticationWireMockServer;
    private transient WireMockServer fieldsWireMockServer;

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
    private transient FieldCommunicationController communicationController;

    @Test
    @WithMockUser
    public void adminDeleteFieldTest() {
        fieldsWireMockServer = new WireMockServer(8083);
        fieldsWireMockServer.start();

        authenticationWireMockServer = new WireMockServer(8080);
        authenticationWireMockServer.start();

        authenticationWireMockServer.stubFor(get(urlPathMatching(getUserIdPath))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody("1")));
        fieldsWireMockServer.stubFor(delete(urlPathMatching("/field/delete?([a-z]*)"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody(deleted)));
        String res = communicationController.deleteField("", 1);
        assertEquals(deleted, res);
        authenticationWireMockServer.stop();
        fieldsWireMockServer.stop();
    }

}
