package nl.tudelft.sem.template.reservations.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "equipmentreservation2")
public class EquipmentReservation extends Reservation {

    @Column(name = "equipment_id")
    private Integer equipmentId;

    /**
     * Simple constructor.
     */
    public EquipmentReservation() {
        super();
    }


    /**
     * Constructor method.
     *
     * @param id Reservation id
     * @param equipmentId Equipment id
     * @param reserver Reservation's username
     * @param startTime Start time of reservation
     */
    public EquipmentReservation(Integer id,
                            Integer equipmentId,
                            Integer reserver,
                            LocalDateTime startTime) {
        super(id, reserver, startTime);
        this.equipmentId = equipmentId;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EquipmentReservation equipmentReservation = (EquipmentReservation) o;
        return equipmentId.equals(equipmentReservation.equipmentId)
                && super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}