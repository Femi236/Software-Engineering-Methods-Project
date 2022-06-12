package nl.tudelft.sem.template.gateway.services;

import java.util.List;
import nl.tudelft.sem.template.gateway.RequestBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationCommunicationService {

    private final transient String authenticationPort = "8080";
    private final transient String idParam = "?id=";

    public List<Object> getAllUsers(String token) {
        String res = RequestBuilder.getAuth(authenticationPort, "/demo/all", token);
        return (new JSONArray(res)).toList();
    }

    /**
     * Add user method.
     *
     * @param id of the user
     * @param firstName of the user
     * @param lastName of the user
     * @param password of the user
     * @param role of the user
     * @param netId of the user
     * @return string http request
     */
    public String addUser(int id, String firstName, String lastName,
                          String password, String role, String netId, String token) {
        return RequestBuilder.postAuth(authenticationPort, "/demo/register",
                idParam + id + "&firstName="
                        + firstName + "&lastName=" + lastName + "&password="
                        + password + "&role=" + role + "&netId=" + netId, token);
    }

    /**
     * Delete user from database.
     *
     * @param id of user to be deleted
     * @param token authentication for authorization
     * @return String
     */
    public String deleteUser(int id, String token) {
        return RequestBuilder.deleteAuth(authenticationPort, "/demo/delete", idParam + id, token);
    }

    /**update user method.
     *
     * @param id of the user
     * @param firstName of the user
     * @param lastName of the user
     * @param password of the user
     * @param role of the user
     * @param netId of the user
     * @return string http request
     */
    public String updateUser(int id, String firstName, String lastName,
                             String password, String role, String netId, String token) {
        return RequestBuilder.putAuth(authenticationPort, "/demo/update",
                idParam + id + "&firstName="
                        + firstName + "&lastName=" + lastName + "&password="
                        + password + "&role=" + role + "&netId=" + netId, token);
    }

    /**
     * Get the details of a user by their id.
     *
     * @param token the token of the logged in user
     * @param id the id of the user details to find
     * @return the details of the user
     */
    public Object getUserDetails(String token, int id) {
        String res = RequestBuilder
                .postAuth(authenticationPort, "/demo/getUser", idParam + id, token);
        try {
            return (new JSONObject(res).toMap());
        } catch (Exception e) {
            return "User does not exist";
        }
    }

    /**
     * Adds an admin.
     *
     * @param token the token of the logged in user
     * @param id the id of the user to add
     * @param firstName the first name of teh user to add
     * @param lastName the last name of the user to add
     * @param password the password of the user to add
     * @param role the role of the user to add
     * @param netId the netid of the user to add
     * @return the server response
     */
    public String addAdmin(String token, int id, String firstName, String lastName,
                           String password, String role, String netId) {
        return RequestBuilder
                .postAuth(authenticationPort, "/demo/register", idParam + id + "&firstName="
                        + firstName + "&lastName=" + lastName + "&password="
                        + password + "&role=" + role + "&netId=" + netId, token);
    }

}
