package nl.tudelft.sem.template.field;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/field")
public class FieldController {

    @Autowired
    private transient FieldService fieldService;

    @PostMapping(path = "/add")
    public @ResponseBody
    String addNewField(@RequestParam int id,
                       @RequestParam String name,
                       @RequestParam int minCapacity,
                       @RequestParam int maxCapacity) {

        return fieldService.addNewField(id, name, minCapacity, maxCapacity);
    }

    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Field> getAllFields() {
        return fieldService.getAllFields();
    }

    @PostMapping(path = "/update")
    public @ResponseBody
    String updateField(@RequestParam int id, @RequestParam String name,
                       @RequestParam int minCapacity,
                       @RequestParam int maxCapacity) {
        return fieldService.updateField(id, name, minCapacity, maxCapacity);
    }

    @PostMapping(path = "/delete")
    public @ResponseBody String deleteField(@RequestParam int id) {
        return fieldService.deleteField(id);
    }

    @PostMapping(path = "/changeCapacity")
    public @ResponseBody
    String changeCapacity(@RequestParam int id, @RequestParam int maxCapacity) {
        return fieldService.setCapacity(id, maxCapacity);
    }

}
