package nl.tudelft.sem.template.field;


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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;



@ExtendWith(MockitoExtension.class)
public class FieldServiceTest {

    private transient Field field1;
    private transient Field field2;
    private transient List<Field> fieldList;

    @Mock
    private transient FieldRepository fieldRepository;

    @InjectMocks
    private transient FieldService fieldService;

    /**
     * Initializes attributes before each test.
     */
    @BeforeEach
    public void init() {
        field1 = new Field(1, "tennis court", 2, 4);
        field2 = new Field(2, "dance studio", 1, 6);
        fieldList = new ArrayList<>();

        fieldList.add(field1);
        fieldList.add(field2);
    }

    @Test
    public void correctlySavesEquipment() {
        when(fieldRepository.save(any(Field.class))).then(returnsFirstArg());
        String response = fieldService.addNewField(field1.getId(), field1.getName(),
                field1.getMin_capacity(), field1.getMax_capacity());
        assertEquals("Saved", response);
    }

    @Test
    public void addFieldNegativeCapacity() {
        field1.setMax_capacity(-1);
        String response = fieldService
                .addNewField(field1.getId(), field1.getName(), field1.getMin_capacity(),
                        field1.getMax_capacity());
        assertEquals("min_capacity and max_capacity can not be negative", response);
    }

    @Test
    public void returnsCorrectList() {
        when(fieldRepository.findAll()).thenReturn(fieldList);
        Iterable<Field> response = fieldService.getAllFields();
        assertEquals(fieldList, response);
    }

    @Test
    public void correctlyUpdatesField() {
        when(fieldRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(field1));
        when(fieldRepository.save(any(Field.class))).then(returnsFirstArg());
        String response = fieldService
                .updateField(field1.getId(), field1.getName(), field1.getMin_capacity(),
                        field1.getMax_capacity());
        assertEquals("Updated", response);
    }

    @Test
    public void updateNonExistingField() {
        when(fieldRepository.findById(not(eq(2)))).thenReturn(Optional.ofNullable(null));
        String response = fieldService
                .updateField(field1.getId(), field1.getName(), field1.getMin_capacity(),
                        field1.getMax_capacity());
        assertEquals("field does not exist", response);
    }

    @Test
    public void updateEquipmentNegativeCapacity() {
        when(fieldRepository.findById(any(Integer.class)))
                .thenReturn(Optional.ofNullable(field1));
        field1.setMin_capacity(-1);
        String response = fieldService
                .updateField(field1.getId(), field1.getName(), field1.getMin_capacity(),
                        field1.getMax_capacity());
        assertEquals("min_capacity can not be negative", response);
    }

    @Test
    public void deleteValidField() {
        doNothing().when(fieldRepository).deleteById(any(Integer.class));
        String response = fieldService.deleteField(field1.getId());
        assertEquals("Deleted", response);
    }

    @Test
    public void deleteNonExistingField() {
        doThrow(new EmptyResultDataAccessException(1)).when(fieldRepository)
                .deleteById(any(Integer.class));
        String response = fieldService.deleteField(field1.getId());
        assertEquals("field does not exist", response);
    }


}
