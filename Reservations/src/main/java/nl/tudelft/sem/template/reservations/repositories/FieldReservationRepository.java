package nl.tudelft.sem.template.reservations.repositories;

import nl.tudelft.sem.template.reservations.entities.FieldReservation;
import org.springframework.data.repository.CrudRepository;

//This wil be AUTO IMPLEMENTED by Spring into a Bean called equipmentReservationRepository
//CRUD refers to Create, read, Update, Delete

public interface FieldReservationRepository
        extends CrudRepository<FieldReservation, Integer> {

}

