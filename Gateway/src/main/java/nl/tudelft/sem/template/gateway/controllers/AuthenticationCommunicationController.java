package nl.tudelft.sem.template.gateway.controllers;

import java.util.List;
import nl.tudelft.sem.template.gateway.RequestBuilder;
import nl.tudelft.sem.template.gateway.services.AuthenticationCommunicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/gateway")
public class AuthenticationCommunicationController {

    private final transient String loginError = "LoginError";
    private static final transient String authorization = "Authorization";

    @Autowired
    private transient AuthenticationCommunicationService communicationService;

    @GetMapping(path = "/allUsers")
    public @ResponseBody
    List<Object> getAllUsers(@RequestHeader(name = "Authorization") String token) {
        return communicationService.getAllUsers(token);
    }

    @PostMapping(path = "/addUser")
    public @ResponseBody
    String addUser(@RequestHeader(name = authorization)String token, @RequestParam int id,
                   String firstName, String lastName, String password, String role,
                   String netId) {
        return communicationService.addUser(id, firstName, lastName, password, role, netId, token);
    }

    @DeleteMapping(path = "/deleteUser")
    public @ResponseBody
    String deleteUser(@RequestHeader(name = authorization) String token, @RequestParam int id) {
        return communicationService.deleteUser(id, token);
    }

    @PutMapping(path = "/updateUser")
    public @ResponseBody
    String updateUser(@RequestHeader(name = authorization)String token, @RequestParam int id,
                      String firstName, String lastName,
                      String password, String role, String netId) {
        return communicationService.updateUser(id, firstName, lastName,
                password, role, netId, token);
    }

    @GetMapping(path = "/admin/getUserDetails")
    public @ResponseBody Object
    getUserDetails(@RequestHeader(name = authorization) String token, @RequestParam int id) {
        return communicationService.getUserDetails(token, id);

    }

    @PostMapping(path = "/admin/addAdmin")
    public @ResponseBody
    String addAdmin(@RequestHeader(name = authorization) String token, @RequestParam int id,
                    @RequestParam String firstName, @RequestParam String lastName,
                    @RequestParam String password, @RequestParam String role,
                    @RequestParam String netId) {
        return communicationService.addAdmin(token, id, firstName, lastName, password, role, netId);
    }

    /**
     * Method to login through gateway and get token.
     *
     * @param body the body containing the login details.
     * @return the response.
     */
    @PostMapping(path = "/auth")
    public @ResponseBody
    ResponseEntity<String> login(@RequestBody(required = true) String body) {

        String token = RequestBuilder.postLogIn("8080", "/auth", body);
        if (token == null) {
            return ResponseEntity.ok().body("CommunicationService with server failed");
        }
        if (token.equals(loginError)) {
            return ResponseEntity.ok().body("Incorrect login details");
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Authorization", token);
        return ResponseEntity.ok().headers(responseHeaders).body("Logged in");
    }


}
