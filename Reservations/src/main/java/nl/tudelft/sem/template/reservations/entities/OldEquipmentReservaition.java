//package nl.tudelft.sem.template.reservations.entities;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.Objects;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//@Entity
//@Table(name = "equipmentreservation")
//public class EquipmentReservation {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "equipmentreservation_id")
//    private Integer id;
//
//    @Column(name = "equipment_id")
//    private Integer equipmentId;
//
//    private Integer reserver;
//
//    @Column(name = "start_time")
//    private LocalDateTime startTime;
//
//
//    /**
//     * Simple constructor.
//     */
//    public EquipmentReservation(){
//
//    }
//
//
//    /**
//     * Constructor method.
//     *
//     * @param id Reservation id
//     * @param equipmentId Equipment id
//     * @param reserver Reservation's username
//     * @param startTime Start time of reservation
//     */
//    public EquipmentReservation(Integer id,
//                                Integer equipmentId,
//                                Integer reserver,
//                                LocalDateTime startTime) {
//        this.id = id;
//        this.equipmentId = equipmentId;
//        this.reserver = reserver;
//        this.startTime = startTime;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Integer getEquipmentId() {
//        return equipmentId;
//    }
//
//    public void setEquipmentId(Integer equipmentId) {
//        this.equipmentId = equipmentId;
//    }
//
//    public Integer getReserver() {
//        return reserver;
//    }
//
//    public void setReserver(Integer reserver) {
//        this.reserver = reserver;
//    }
//
//    public LocalDateTime getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(LocalDateTime startTime) {
//        this.startTime = startTime;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) {
//            return true;
//        }
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//
//        EquipmentReservation equipmentReservation = (EquipmentReservation) o;
//        return id.equals(equipmentReservation.id)
//                && equipmentId.equals(equipmentReservation.equipmentId)
//                && reserver.equals(equipmentReservation.reserver)
//                && startTime.equals(equipmentReservation.startTime);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, equipmentId, reserver, startTime);
//    }
//}