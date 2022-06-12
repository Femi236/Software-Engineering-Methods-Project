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
//@Table(name = "fieldreservation")
//public class FieldReservation {
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "fieldreservation_id")
//    private Integer id;
//
//    @Column(name = "field")
//
//    private Integer fieldId;
//
//    private Integer reserver;
//
//    @Column(name = "start_time")
//    private LocalDateTime startTime;
//
//    @Column(name = "team")
//    private Integer group;
//
//
//    /**
//     * Simple constructor.
//     */
//    public FieldReservation(){
//
//    }
//
//
//    /**
//     * Constructor method.
//     *
//     * @param id Reservation id
//     * @param fieldId Field id
//     * @param reserver the Id of reserver
//     * @param startTime Start time of reservation
//     * @param group the Id of the group
//     */
//    public FieldReservation(Integer id,
//                            Integer fieldId,
//                            Integer reserver,
//                            LocalDateTime startTime,
//                            Integer group) {
//        this.id = id;
//        this.fieldId = fieldId;
//        this.reserver = reserver;
//        this.startTime = startTime;
//        this.group = group;
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
//    public Integer getFieldId() {
//        return fieldId;
//    }
//
//    public void setFieldId(Integer fieldId) {
//        this.fieldId = fieldId;
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
//    public Integer getGroup() {
//        return group;
//    }
//
//    public void setGroup(Integer group) {
//        this.group = group;
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
//        FieldReservation fieldReservation = (FieldReservation) o;
//        return id.equals(fieldReservation.id)
//                && fieldId.equals(fieldReservation.fieldId)
//                && reserver.equals(fieldReservation.reserver)
//                && startTime.equals(fieldReservation.startTime)
//                && group.equals(fieldReservation.group);
//    }
//
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, fieldId, reserver, startTime, group);
//    }
//}