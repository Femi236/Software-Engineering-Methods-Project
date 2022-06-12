package nl.tudelft.sem.template.equipment.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import nl.tudelft.sem.template.equipment.entities.Equipment;
import nl.tudelft.sem.template.equipment.repositories.EquipmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;


@ExtendWith(MockitoExtension.class)
public class EquipmentServiceTest {
    private transient Equipment equipment1;
    private transient Equipment equipment2;
    private transient Equipment equipment3;
    private transient List<Equipment> equipmentList;

    @Mock
    private transient EquipmentRepository equipmentRepository;

    @InjectMocks
    private transient EquipmentService equipmentService;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        equipment1 = new Equipment(1, 10);
        equipmentList = new ArrayList<>();
        equipment2 = new Equipment(2, 20);
        equipment3 = new Equipment(3, 5);

        equipmentList.add(equipment1);
        equipmentList.add(equipment2);
        equipmentList.add(equipment3);
    }

    @Test //YEs
    public void correctlySavesEquipment() {
        when(equipmentRepository.save(any(Equipment.class))).then(returnsFirstArg());
        String response = equipmentService
                .addNewEquipment(equipment1.getType());
        assertEquals("Saved", response);
    }

    @Test//yes
    public void addEquipmentNegativeType() {
        equipment1.setType(-1);
        String response = equipmentService
                .addNewEquipment(equipment1.getType());
        assertEquals("Type cannot be negative", response);
    }

    @Test //YEs
    public void returnsCorrectEquipment() {
        when(equipmentRepository.findAll()).thenReturn(equipmentList);
        Iterable<Equipment> response = equipmentService.getAllEquipment();
        assertEquals(equipmentList, response);
    }

    @Test
    public void correctlyUpdatesEquipment() {
        when(equipmentRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(equipment1));
        when(equipmentRepository.save(any(Equipment.class))).then(returnsFirstArg());
        String response = equipmentService
                .updateEquipment(equipment1.getId(), equipment2.getType());
        assertEquals("Updated", response);
    }

    @Test
    public void updateNonExistingEquipment() {
        when(equipmentRepository.findById(not(eq(2)))).thenReturn(Optional.ofNullable(null));
        String response = equipmentService
                .updateEquipment(equipment1.getId(), equipment2.getType());
        assertEquals("Equipment does not exist", response);
    }

    @Test
    public void updateEquipmentNegativeType() {
        when(equipmentRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(equipment1));
        equipment1.setType(-1);
        String response = equipmentService
                .updateEquipment(equipment1.getId(), equipment1.getType());
        assertEquals("Type cannot be negative", response);
    }

    @Test //yes
    public void deleteValidEquipment() {
        doNothing().when(equipmentRepository).deleteById(any(Integer.class));
        String response = equipmentService.deleteEquipment(equipment1.getId());
        assertEquals("Deleted", response);
    }

    @Test //yes
    public void deleteNonExistingEquipment() {
        doThrow(new EmptyResultDataAccessException(1)).when(equipmentRepository)
                .deleteById(any(Integer.class));
        String response = equipmentService.deleteEquipment(equipment1.getId());
        assertEquals("Equipment does not exist", response);
    }


}
