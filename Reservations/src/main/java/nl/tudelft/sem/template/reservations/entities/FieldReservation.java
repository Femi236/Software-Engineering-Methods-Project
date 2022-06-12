package nl.tudelft.sem.template.reservations.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "fieldreservation2")
public class FieldReservation extends Reservation {

    @Column(name = "field")
    private Integer fieldId;

    @Column(name = "team")
    private Integer group;


    /**
     * Simple constructor.
     */
    public FieldReservation() {
        super();
    }


    /**
     * Constructor method.
     *
     * @param id Reservation id
     * @param fieldId Field id
     * @param reserver the Id of reserver
     * @param startTime Start time of reservation
     * @param group the Id of the group
     */
    public FieldReservation(Integer id,
                                Integer fieldId,
                                Integer reserver,
                                LocalDateTime startTime,
                                Integer group) {
        super(id, reserver, startTime);
        this.fieldId = fieldId;
        this.group = group;
    }


    public Integer getFieldId() {
        return fieldId;
    }

    public void setFieldId(Integer fieldId) {
        this.fieldId = fieldId;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FieldReservation fieldReservation = (FieldReservation) o;
        return fieldId.equals(fieldReservation.fieldId)
                && group.equals(fieldReservation.group) && super.equals(o);
    }


    @Override
    public int hashCode() {
        return super.hashCode();
    }

}