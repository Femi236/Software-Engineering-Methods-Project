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
import nl.tudelft.sem.template.equipment.entities.EquipmentType;
import nl.tudelft.sem.template.equipment.repositories.EquipmentTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;


@ExtendWith(MockitoExtension.class)
public class EquipmentTypeServiceTest {
    private transient EquipmentType equipmentType1;
    private transient EquipmentType equipmentType2;
    private transient EquipmentType equipmentType3;
    private transient List<EquipmentType> equipmentTypeList;

    @Mock
    private transient EquipmentTypeRepository equipmentTypeRepository;

    @InjectMocks
    private transient EquipmentTypeService equipmentTypeService;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        equipmentType1 = new EquipmentType(1, "ball");
        equipmentTypeList = new ArrayList<>();
        equipmentType2 = new EquipmentType(2, "bat");
        equipmentType3 = new EquipmentType(3, "boxing gloves");

        equipmentTypeList.add(equipmentType1);
        equipmentTypeList.add(equipmentType2);
        equipmentTypeList.add(equipmentType3);
    }

    @Test
    public void correctlySavesEquipmentType() {
        when(equipmentTypeRepository.save(any(EquipmentType.class))).then(returnsFirstArg());
        String response = equipmentTypeService
                .addNewEquipmentType(equipmentType1.getName());
        assertEquals("Saved", response);
    }

    @Test
    public void addEquipmentTypeNullName() {
        equipmentType1.setName(null);
        String response = equipmentTypeService
                .addNewEquipmentType(equipmentType1.getName());
        assertEquals("Name cannot be null", response);
    }

    @Test
    public void returnsCorrectEquipmentType() {
        when(equipmentTypeRepository.findAll()).thenReturn(equipmentTypeList);
        Iterable<EquipmentType> response = equipmentTypeService.getAllEquipmentType();
        assertEquals(equipmentTypeList, response);
    }

    @Test
    public void correctlyUpdatesEquipmentType() {
        when(equipmentTypeRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(equipmentType1));
        when(equipmentTypeRepository.save(any(EquipmentType.class))).then(returnsFirstArg());
        String response = equipmentTypeService
                .updateEquipmentType(equipmentType1.getId(), equipmentType1.getName());
        assertEquals("Updated", response);
    }

    @Test
    public void updateNonExistingEquipmentType() {
        when(equipmentTypeRepository.findById(not(eq(2)))).thenReturn(Optional.ofNullable(null));
        String response = equipmentTypeService
                .updateEquipmentType(equipmentType1.getId(), equipmentType1.getName());
        assertEquals("EquipmentType does not exist", response);
    }

    @Test
    public void updateEquipmentTypeNullName() {
        when(equipmentTypeRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(equipmentType1));
        equipmentType1.setName(null);
        String response = equipmentTypeService
                .updateEquipmentType(equipmentType1.getId(), equipmentType1.getName());
        assertEquals("Name cannot be null", response);
    }

    @Test
    public void deleteValidEquipmentType() {
        doNothing().when(equipmentTypeRepository).deleteById(any(Integer.class));
        String response = equipmentTypeService.deleteEquipmentType(equipmentType1.getId());
        assertEquals("Deleted", response);
    }

    @Test
    public void deleteNonExistingEquipmentType() {
        doThrow(new EmptyResultDataAccessException(1)).when(equipmentTypeRepository)
                .deleteById(any(Integer.class));
        String response = equipmentTypeService.deleteEquipmentType(equipmentType1.getId());
        assertEquals("EquipmentType does not exist", response);
    }


}
