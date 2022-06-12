package nl.tudelft.sem.template.reservations.repositories;


import nl.tudelft.sem.template.reservations.entities.EquipmentReservation;
import org.springframework.data.repository.CrudRepository;

//This wil be AUTO IMPLEMENTED by Spring into a Bean called equipmentReservationRepository
//CRUD refers to Create, read, Update, Delete

public interface EquipmentReservationRepository
        extends CrudRepository<EquipmentReservation, Integer> {

}

