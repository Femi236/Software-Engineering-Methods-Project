package nl.tudelft.sem.template.equipment.controllers;


import nl.tudelft.sem.template.equipment.entities.Equipment;
import nl.tudelft.sem.template.equipment.services.EquipmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/equipment") // This means URL's start with /demo (after Application path)
public class EquipmentController {

    @Autowired
    private transient EquipmentService equipmentService;

    /**
     * Creates a new Equipment.
     *
     * @param type the type of the equipment
     * @return the status of the request
     */
    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewEquipment(@RequestParam int type) {
        return equipmentService.addNewEquipment(type);
    }

    /**
     * Gets all equipment.
     *
     *  @return the status of the request
     */
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<Equipment> getAllEquipment() {
        // This returns a JSON or XML with the users
        return equipmentService.getAllEquipment();
    }

    /**
     * Updates an Equipment.
     *
     * @param id the id of the equipment to update
     * @param type the type of the equipment
     * @return the status of the request
     */
    @PostMapping(path = "/update")
    public @ResponseBody String updateEquipment(@RequestParam int id, @RequestParam int type) {
        return equipmentService.updateEquipment(id, type);
    }

    /**
     * Deletes an Equipment by the id.
     *
     * @param id the id of the equipment to delete
     * @return the status of the request
     */
    @DeleteMapping(path = "/delete")
    public @ResponseBody String deleteEquipment(@RequestParam int id) {
        return equipmentService.deleteEquipment(id);
    }



}
