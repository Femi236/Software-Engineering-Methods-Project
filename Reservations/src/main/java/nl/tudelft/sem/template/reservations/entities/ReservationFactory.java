package nl.tudelft.sem.template.reservations.entities;

import java.util.Locale;

public class ReservationFactory extends BaseReservationFactory {

    @Override
    public Reservation createReservation(String type) {
        Reservation reservation;
        String t = type.toLowerCase(Locale.ENGLISH);
        if (t.equals("equipment")) {
            reservation = new EquipmentReservation();
        } else if (t.equals("field")) {
            reservation = new FieldReservation();
        } else {
            throw new IllegalArgumentException("No such reservation");
        }
        return reservation;
    }
}
