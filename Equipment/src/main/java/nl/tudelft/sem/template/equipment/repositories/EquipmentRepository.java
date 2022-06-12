package nl.tudelft.sem.template.equipment.repositories;

import nl.tudelft.sem.template.equipment.entities.Equipment;
import org.springframework.data.repository.CrudRepository;


public interface EquipmentRepository extends CrudRepository<Equipment, Integer> {

}
