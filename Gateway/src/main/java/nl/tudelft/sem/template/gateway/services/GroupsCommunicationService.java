package nl.tudelft.sem.template.gateway.services;

import nl.tudelft.sem.template.gateway.RequestBuilder;
import org.springframework.stereotype.Service;

@Service
public class GroupsCommunicationService {

    private final transient String authenticationPort = "8080";
    private final transient String groupPort = "8084";
    private final transient String userIdPath = "/demo/getUserId";
    private final transient String idParam = "?id=";

    public String addGroupMember(int group, int user) {
        return RequestBuilder.post(groupPort, "/groupUser/add",
                "?group=" + group + "&user=" + user);
    }

    public String deleteGroupMember(int group, int user) {
        return RequestBuilder.post(groupPort, "/groupUser/delete",
                "?group=" + group + "&user=" + user);
    }

    public String deleteGroup(int id) {
        return RequestBuilder.post(groupPort, "/group/delete",
                idParam + id);
    }

    /**
     * Deletes the user that calls this method from the specified group.
     *
     * @param token the token of the logged in user
     * @param group the group the user is to be deleted from
     * @return the status of the request
     */
    public String leaveGroup(String token, int group) {
        String userId = RequestBuilder.getAuth("8080", "/demo/getUserId", token);
        return RequestBuilder.post(groupPort, "/groupUser/delete",
                "?group=" + group + "&user=" + userId);
    }

    /**
     * Adds a new group.
     *
     * @param token the token of the logged in user
     * @return the server response
     */
    public String addNewGroup(String token) {
        String creator = RequestBuilder.getAuth(authenticationPort, userIdPath, token);
        return RequestBuilder.post(groupPort, "/group/add", "?creator=" + creator);
    }

}
