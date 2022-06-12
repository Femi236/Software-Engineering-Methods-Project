package nl.tudelft.sem.template.equipment.services;

//import nl.tudelft.sem.template.equipmentType.NegativeStockException;
import nl.tudelft.sem.template.equipment.entities.EquipmentType;
import nl.tudelft.sem.template.equipment.repositories.EquipmentTypeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EquipmentTypeService {

    private transient EquipmentTypeRepository equipmentTypeRepository;

    public EquipmentTypeService(EquipmentTypeRepository equipmentTypeRepository) {
        this.equipmentTypeRepository = equipmentTypeRepository;
    }

    /**
     * Creates a new EquipmentType.
     *
     * @param name the name of the equipmentType
     * @return the status of the request
     */
    public String addNewEquipmentType(String name) {
        if (name == null) {
            return "Name cannot be null";
        }
        EquipmentType equipmentType = new EquipmentType();
        equipmentType.setName(name);
        try {
            equipmentTypeRepository.save(equipmentType);
            return "Saved";
        } catch (DataIntegrityViolationException e) {
            return "EquipmentType with name '" + name + "' already exists";
        }

    }

    /**
     * Gets all equipmentType.
     *
     *  @return the status of the request
     */
    public Iterable<EquipmentType> getAllEquipmentType() {
        return equipmentTypeRepository.findAll();
    }

    /**
     * Updates an EquipmentType.
     *
     * @param id the id of the equipmentType to update
     * @param name the name of the equipmentType to update
     * @return the status of the request
     */
    public String updateEquipmentType(int id, String name) {
        EquipmentType equipmentType = equipmentTypeRepository.findById(id).orElse(null);
        if (equipmentType == null) {
            return "EquipmentType does not exist";
        }
        if (name == null) {
            return "Name cannot be null";
        }
        equipmentType.setName(name);
        try {
            equipmentTypeRepository.save(equipmentType);
            return "Updated";
        } catch (DataIntegrityViolationException e) {
            return "EquipmentType with name '" + name + "' already exists";
        }
    }

    /**
     * Deletes an EquipmentType by the id.
     *
     * @param id the id of the equipmentType to delete
     * @return the status of the request
     */
    public String deleteEquipmentType(int id) {
        try {
            equipmentTypeRepository.deleteById(id);
            return "Deleted";
        } catch (EmptyResultDataAccessException e) {
            return "EquipmentType does not exist";
        }
    }

}
