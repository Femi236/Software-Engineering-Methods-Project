package nl.tudelft.sem.template.gateway.services;

import java.util.List;
import nl.tudelft.sem.template.gateway.RequestBuilder;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class EquipmentCommunicationService {

    private final transient String equipmentPort = "8082";
    private final transient String idParam = "?id=";

    public String addNewEquipment(int type) {
        return RequestBuilder.post(equipmentPort, "/equipment/add", "?type=" + type);
    }

    public List<Object> getAllEquipment() {
        String res = RequestBuilder.get(equipmentPort, "/equipment/all");
        return (new JSONArray(res)).toList();
    }

    /**
     * Adds a new Type of equipment (admin).
     *
     * @param type the type to be added
     * @return server response
     */
    public String addNewEquipType(String type) {
        return RequestBuilder.post(equipmentPort, "/equipmentType/add", "?=name" + type);
    }

    public String addEquipment(int type) {
        return RequestBuilder.post("8082", "/equipment/add", "?type=" + type);
    }

    /**admin delete equipment method.
     *
     * @param id of the equipment
     * @param token the token of the logged in user
     * @return server response
     */
    public String deleteEquipment(int id, String token) {
        //String userId = RequestBuilder.getAuth(authenticationPort, userIdPath, token);
        return  RequestBuilder.delete(equipmentPort, "/equipment/delete", idParam + id);
    }

}
