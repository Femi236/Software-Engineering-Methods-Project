package nl.tudelft.sem.template.reservations.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


@MappedSuperclass
public abstract class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer reserver;
    private LocalDateTime startTime;

    public Reservation() {
    }

    /**
     * Constructor.
     *
     * @param id the id of the reservation
     * @param reserver the id of the person that made the reservation
     * @param startTime the start time of the reservation
     */
    public Reservation(Integer id,  Integer reserver, LocalDateTime startTime) {
        this.id = id;
        this.reserver = reserver;
        this.startTime = startTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getReserver() {
        return reserver;
    }

    public void setReserver(Integer reserver) {
        this.reserver = reserver;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Reservation reservation = (Reservation) o;
        return id.equals(reservation.id)
                && reserver.equals(reservation.reserver)
                && startTime.equals(reservation.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, reserver, startTime);
    }
}
