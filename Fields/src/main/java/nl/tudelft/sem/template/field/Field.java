package nl.tudelft.sem.template.field;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "field")
public class Field {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "field_id")
    private int id;

    private String name;

    private transient int minCapacity;

    private transient int maxCapacity;

    public Field() {

    }

    /**
     * constructor method.
     *
     * @param id field id
     * @param name of the field
     * @param minCapacity minimal capacity
     * @param maxCapacity maximum capacity
     */
    public Field(int id, String name, int minCapacity, int maxCapacity) {
        this.id = id;
        this.name = name;
        this.minCapacity = minCapacity;
        this.maxCapacity = maxCapacity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMin_capacity() {
        return minCapacity;
    }

    public void setMin_capacity(int minCapacity) {
        this.minCapacity = minCapacity;
    }

    public int getMax_capacity() {
        return maxCapacity;
    }

    public void setMax_capacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Field field = (Field) o;
        return id == field.getId()
                && name == field.getName()
                && minCapacity == field.getMin_capacity()
                && maxCapacity == field.getMax_capacity();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, minCapacity, maxCapacity);
    }
}
