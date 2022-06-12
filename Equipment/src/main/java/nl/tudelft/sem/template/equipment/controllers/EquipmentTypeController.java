package nl.tudelft.sem.template.equipment.controllers;


import nl.tudelft.sem.template.equipment.entities.EquipmentType;
import nl.tudelft.sem.template.equipment.services.EquipmentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/equipmentType")
public class EquipmentTypeController {

    @Autowired
    private transient EquipmentTypeService equipmentTypeService;

    /**
     * Creates a new EquipmentType.
     *
     * @param name the name of the equipmentType
     * @return the status of the request
     */
    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewEquipmentType(@RequestParam String name) {


        return equipmentTypeService.addNewEquipmentType(name);
    }

    /**
     * Gets all equipmentType.
     *
     *  @return the status of the request
     */
    @GetMapping(path = "/all")
    public @ResponseBody Iterable<EquipmentType> getAllEquipmentType() {
        // This returns a JSON or XML with the users
        return equipmentTypeService.getAllEquipmentType();
    }

    /**
     * Updates an EquipmentType.
     *
     * @param id the id of the equipmentType to update
     * @param name the name of the equipmentType to update
     * @return the status of the request
     */
    @PostMapping(path = "/update")
    public @ResponseBody
    String updateEquipmentType(@RequestParam int id, @RequestParam String name) {
        return equipmentTypeService.updateEquipmentType(id, name);
    }

    /**
     * Deletes an EquipmentType by the id.
     *
     * @param id the id of the equipmentType to delete
     * @return the status of the request
     */
    @PostMapping(path = "/delete")
    public @ResponseBody String deleteEquipmentType(@RequestParam int id) {
        return equipmentTypeService.deleteEquipmentType(id);
    }



}