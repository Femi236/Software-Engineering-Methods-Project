package nl.tudelft.sem.template.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class FieldService {
    @Autowired
    private transient FieldRepository fieldRepository;

    public FieldService(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    /**add new field method.
     *
     * @param id of the field
     * @param name of the field
     * @param minCapacity of the field
     * @param maxCapacity of the field
     * @return string saved
     */
    public String addNewField(int id, String name, int minCapacity, int maxCapacity) {

        if (name == null) {
            return "Name cannot be null";
        }
        if (minCapacity < 0 || maxCapacity < 0) {
            return "min_capacity and max_capacity can not be negative";
        }
        Field f = new Field();
        f.setId(id);
        f.setName(name);
        f.setMin_capacity(minCapacity);
        f.setMax_capacity(maxCapacity);
        fieldRepository.save(f);
        return "Saved";

    }

    public Iterable<Field> getAllFields() {
        return fieldRepository.findAll();
    }

    /**update method.
     *
     * @param id of the field
     * @param name of the field
     * @param minCapacity of the field
     * @param maxCapacity of the field
     * @return string updated
     */
    public String updateField(int id, String name, int minCapacity, int maxCapacity) {
        Field f = fieldRepository.findById(id).orElse(null);
        if (f == null) {
            return "field does not exist";
        }
        if (name == null) {
            return "Name cannot be null";
        }
        if (minCapacity < 0) {
            return "min_capacity can not be negative";
        }
        f.setId(id);
        f.setName(name);
        f.setMin_capacity(minCapacity);
        f.setMax_capacity(maxCapacity);
        fieldRepository.save(f);
        return "Updated";
    }

    /**delete method.
     *
     * @param id of the field
     * @return string deleted
     */
    public String deleteField(int id) {
        try {
            fieldRepository.deleteById(id);
            return "Deleted";
        } catch (EmptyResultDataAccessException e) {
            return "field does not exist";
        }
    }

    /**
     * Change the maximum capacity for a certain field.
     *
     * @param id of the field
     * @param maxCapacity new maximum capacity
     * @return string Maximum capacity updated
     */
    public String setCapacity(int id, int maxCapacity) {
        Field f = fieldRepository.findById(id).orElse(null);
        f.setMax_capacity(maxCapacity);
        fieldRepository.save(f);
        return "Maximum capacity updated";
    }

}
