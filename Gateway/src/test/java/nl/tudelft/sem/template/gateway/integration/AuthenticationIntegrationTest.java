package nl.tudelft.sem.template.gateway.integration;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.delete;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.github.tomakehurst.wiremock.WireMockServer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nl.tudelft.sem.template.gateway.controllers.AuthenticationCommunicationController;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

@SpringBootTest
public class AuthenticationIntegrationTest {

    private transient String jsonString;
    private transient List<String> expectedJsonArray;
    private transient JSONArray jsonArray;

    private transient String contentType = "Content-Type";
    private transient String applicationJson = "application/json";

    private transient String deleted = "Deleted";
    private transient String test = "test";

    private transient WireMockServer authenticationWireMockServer;

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
    private transient AuthenticationCommunicationController communicationController;

    @Test
    @WithMockUser
    public void demoMethod() {
        authenticationWireMockServer = new WireMockServer(8080);
        authenticationWireMockServer.start();

        authenticationWireMockServer.stubFor(delete(urlPathMatching("/demo/delete?([a-z]*)"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody(deleted)));
        String res = communicationController.deleteUser("", 5);

        assertEquals(deleted, res);

        authenticationWireMockServer.stop();
    }

    @Test
    @WithMockUser
    public void whenGetUser_thenReturnsUser() {
        authenticationWireMockServer = new WireMockServer(8080);
        authenticationWireMockServer.start();

        Map<String, String> expectedObj = new HashMap<>();
        expectedObj.put("username", "name");

        String obj = "{'username': 'name'}";
        JSONObject jsonObject = new JSONObject(obj);

        authenticationWireMockServer.stubFor(post(urlPathMatching("/demo/getUser?([a-z]*)"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody(jsonObject.toString())));

        Object res = communicationController.getUserDetails("", 5);

        assertEquals(expectedObj, res);

        authenticationWireMockServer.stop();
    }

    @Test
    @WithMockUser
    public void updateUserTest() {
        authenticationWireMockServer = new WireMockServer(8080);
        authenticationWireMockServer.start();

        authenticationWireMockServer.stubFor(put(urlPathMatching("/demo/update?([a-z]*)"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody("")));
        String res = communicationController.updateUser("", 5, "test", "test",
                "password", "admin", "netid");
        assertEquals(null, res);

        authenticationWireMockServer.stop();

    }

    @Test
    @WithMockUser
    public void addUserTest() {
        authenticationWireMockServer = new WireMockServer(8080);
        authenticationWireMockServer.start();

        authenticationWireMockServer.stubFor(post(urlPathMatching("/demo/register?([a-z]*)"))
                .willReturn(aResponse().withFixedDelay(1000)
                        .withStatus(200).withHeader(contentType, applicationJson)
                        .withBody("")));
        String res = communicationController.addUser("", 5, test, test,
                "password", "admin", "netid");
        assertEquals(null, res);

        authenticationWireMockServer.stop();
    }

}
