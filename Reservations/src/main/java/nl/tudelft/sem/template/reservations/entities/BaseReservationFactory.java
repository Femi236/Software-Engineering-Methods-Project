package nl.tudelft.sem.template.reservations.entities;

public abstract class BaseReservationFactory {

    public abstract Reservation createReservation(String type);
}
