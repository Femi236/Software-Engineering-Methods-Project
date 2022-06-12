package nl.tudelft.sem.template.gateway.services;

import java.time.LocalDateTime;
import java.util.List;
import nl.tudelft.sem.template.gateway.RequestBuilder;
import org.json.JSONArray;
import org.springframework.stereotype.Service;

@Service
public class ReservationCommunicationService {

    private final transient String authenticationPort = "8080";
    private final transient String equipmentPort = "8082";
    private final transient String fieldPort = "8083";
    private final transient String groupPort = "8084";
    private final transient String reservationPort = "8085";
    private final transient String userIdPath = "/demo/getUserId";
    private final transient String idParam = "?id=";


    /**
     * Get the equipment reservations for a specific user.
     *
     * @param token the token of the logged in user
     * @return the equipment reservations
     */
    public List<Object> getEquipmentReservations(String token) {
        String userId = RequestBuilder.getAuth(authenticationPort, userIdPath, token);
        //int userId = Integer.parseInt(user);
        String res = RequestBuilder.post("8085", "/equipmentreservation/userReservations",
                idParam + userId);
        return (new JSONArray(res)).toList();
    }

    /**
     * Get all reservations for an equipment.
     *
     * @param id the id of the equipment
     * @return returns a list of all reservations for that equipment id
     */
    public List<Object> getEquipmentReservationsForEquipment(int id) {
        String res = RequestBuilder
                .post("8085", "/equipmentreservation/reservationsForEquipment", idParam + id);
        return (new JSONArray(res)).toList();
    }

    /**
     * Make a field reservation for one person.
     *
     * @param token the token of the logged in user
     * @param id the id of the reservation
     * @param fieldId the id of the field
     * @param startTime the start time of the reservation
     * @return the server response
     */
    public String singleFieldReservation(
            String token, int id, int fieldId, LocalDateTime startTime) {
        String userId = RequestBuilder.getAuth(authenticationPort, userIdPath, token);
        String res = RequestBuilder.post(reservationPort, "/fieldreservation/add",
                idParam + id + "&fieldId=" + fieldId + "&reserver=" + userId + "&startTime="
                        + startTime + "&group=-1");

        return res;
    }

    /**
     * Make a field reservation for a group.
     *
     * @param token the token of the logged in user
     * @param id the id of the reservation
     * @param fieldId the id of the field
     * @param startTime the start time of the reservation
     * @param group the group id for the reservation
     * @return the server response
     */
    public String groupFieldReservation(
            String token, int id, int fieldId, LocalDateTime startTime, int group) {
        String userId = RequestBuilder.getAuth(authenticationPort, userIdPath, token);
        String res = RequestBuilder.post(reservationPort, "/fieldreservation/add",
                idParam + id + "&fieldId=" + fieldId + "&reserver=" + userId + "&startTime="
                        + startTime + "&group=" + group);

        return res;
    }

    /**
     * Make an equipment reservation.
     *
     * @param token the token of the logged in user
     * @param id the id of the reservation
     * @param equipmentId the id of the equipment
     * @param startTime the start time of the reservation
     * @return the server response
     */
    public String equipmentReservation(
            String token, int id, int equipmentId, LocalDateTime startTime) {
        String userId = RequestBuilder.getAuth(authenticationPort, userIdPath, token);
        String res = RequestBuilder.post(reservationPort, "/equipmentreservation/add",
                idParam + id + "&equipmentId=" + equipmentId + "&reserver=" + userId
                        + "&startTime=" + startTime);

        return res;
    }

    /**
     * Deletes an EquipmentReservation.
     *
     * @param id the id of the EquipmentReservation to be deleted
     * @return the server response
     */
    public String deleteEquipRes(int id) {
        return RequestBuilder.post(reservationPort, "/equipmentreservation/delete", "?id=" + id);
    }

    /**
     * Deletes a FieldReservation.
     *
     * @param id the id of the FieldReservation to be deleted
     * @return the server response
     */
    public String deleteFieldRes(int id) {
        return RequestBuilder.post(reservationPort, "/fieldreservation/delete", "?id=" + id);
    }

    /**
     * Get all the FieldReservations for the current user.
     *
     * @param token the token of the logged in user
     * @return the FieldReservations
     */
    public List<Object> getFieldReservations(String token) {
        String userId = RequestBuilder.getAuth(authenticationPort, userIdPath, token);
        String res = RequestBuilder
                .post(reservationPort, "/fieldreservation/userReservations", idParam + userId);
        return (new JSONArray(res)).toList();
    }

    /**
     * Get all the EquipmentReservations.
     *
     * @return the EquipmentReservations
     */
    public List<Object> getAllEquipRes() {
        String res = RequestBuilder.get(reservationPort, "/equipmentreservation/all");
        return (new JSONArray(res).toList());
    }

    /**
     * Gets all the FieldReservations.
     *
     * @return all the FieldReservations
     */
    public List<Object> getAllFieldRes() {
        String res = RequestBuilder.get(reservationPort, "/fieldreservation/all");
        return (new JSONArray(res).toList());
    }

}
