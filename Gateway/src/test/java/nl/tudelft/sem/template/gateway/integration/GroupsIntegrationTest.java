package nl.tudelft.sem.template.gateway.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.ArrayList;
import java.util.List;
import nl.tudelft.sem.template.gateway.controllers.GroupsCommunicationController;
import org.json.JSONArray;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class GroupsIntegrationTest {

    private transient String jsonString;
    private transient List<String> expectedJsonArray;
    private transient JSONArray jsonArray;

    private transient String contentType = "Content-Type";
    private transient String applicationJson = "application/json";

    private transient String saved = "Saved";

    private transient String getUserIdPath = "/demo/getUserId?([a-z]*)";

    private transient WireMockServer authenticationWireMockServer;
    private transient WireMockServer groupsWireMockServer;

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
    private transient GroupsCommunicationController communicationController;

    @Test
    @WithMockUser
    public void whenCreateGroup_thenReturnSaved() {
        groupsWireMockServer = new WireMockServer(8084);
        groupsWireMockServer.start();

        authenticationWireMockServer = new WireMockServer(8080);
        authenticationWireMockServer.start();

        authenticationWireMockServer.stubFor(get(urlPathMatching(getUserIdPath))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody("1"))); //??!!
        groupsWireMockServer.stubFor(post(urlPathMatching(
                "/group/add?([a-z]*)"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody(saved)));

        String res = communicationController.addNewGroup("");
        assertEquals(saved, res);

        authenticationWireMockServer.stop();
        groupsWireMockServer.stop();
    }
}
