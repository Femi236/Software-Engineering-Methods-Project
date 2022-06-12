package nl.tudelft.sem.template.equipment.services;

//import nl.tudelft.sem.template.equipment.NegativeStockException;
import nl.tudelft.sem.template.equipment.entities.Equipment;
import nl.tudelft.sem.template.equipment.repositories.EquipmentRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EquipmentService {

    private transient EquipmentRepository equipmentRepository;

    public EquipmentService(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    /**
     * Creates a new Equipment.
     *
     * @param type the type of the equipment
     * @return the status of the request
     */
    public String addNewEquipment(int type) {
        if (type < 0) {
            return "Type cannot be negative";
        } else {
            Equipment e = new Equipment();
            e.setType(type);
            equipmentRepository.save(e);
            return "Saved";
        }
    }

    /**
     * Gets all equipment.
     *
     *  @return the status of the request
     */
    public Iterable<Equipment> getAllEquipment() {
        return equipmentRepository.findAll();
    }

    /**
     * Updates an Equipment.
     *
     * @param id the id of the equipment to update
     * @param type the type of the equipment
     * @return the status of the request
     */
    public String updateEquipment(int id, int type) {
        Equipment equipment = equipmentRepository.findById(id).orElse(null);
        if (equipment == null) {
            return "Equipment does not exist";
        }
        if (type < 0) {
            return "Type cannot be negative";
        }
        equipment.setType(type);
        equipmentRepository.save(equipment);
        return "Updated";
    }

    /**
     * Deletes an Equipment by the id.
     *
     * @param id the id of the equipment to delete
     * @return the status of the request
     */
    public String deleteEquipment(int id) {
        try {
            equipmentRepository.deleteById(id);
            return "Deleted";
        } catch (EmptyResultDataAccessException e) {
            return "Equipment does not exist";
        }
    }

}
