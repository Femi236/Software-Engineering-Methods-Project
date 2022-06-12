package nl.tudelft.sem.template.equipment.repositories;

import nl.tudelft.sem.template.equipment.entities.EquipmentType;
import org.springframework.data.repository.CrudRepository;


// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface EquipmentTypeRepository extends CrudRepository<EquipmentType, Integer> {

}