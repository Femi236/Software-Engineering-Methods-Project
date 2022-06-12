package nl.tudelft.sem.template.gateway.services;

import nl.tudelft.sem.template.gateway.RequestBuilder;
import org.springframework.stereotype.Service;

@Service
public class FieldCommunicationService {

    private final transient String fieldPort = "8083";
    private final transient String idParam = "?id=";

    /**
     * Adds a new field.
     *
     * @param id id of the field
     * @param name name of the field to be added
     * @param minCapacity The min capacity of the field
     * @param maxCapacity the max capacity of the field
     * @return the status of the request
     */
    public String addField(int id, String name, int minCapacity, int maxCapacity) {
        return RequestBuilder.post("8083", "/field/add",
                idParam + id + "&name=" + name
                        + "&minCapacity=" + minCapacity + "&maxCapacity=" + maxCapacity);
    }

    public String changeCapacity(int id, int maxCapacity) {
        return RequestBuilder.post("8083", "/field/changeCapacity",
                idParam + id + "&maxCapacity=" + maxCapacity);
    }

    /**admin delete field method.
     *
     * @param id of the field
     * @param token the token of the logged in user
     * @return server response
     */
    public String deleteField(int id, String token) {
        //String userId = RequestBuilder.getAuth(authenticationPort, userIdPath, token);
        return  RequestBuilder.delete(fieldPort, "/field/delete", idParam + id);
    }

}
