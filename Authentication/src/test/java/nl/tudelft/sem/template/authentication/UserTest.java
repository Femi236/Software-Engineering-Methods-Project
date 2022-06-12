
package nl.tudelft.sem.template.authentication;




import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class UserTest {

    private transient int id;

    private transient String firstName;

    private transient Integer id2;

    private transient String lastName;

    private transient String password;

    private transient String role;

    private transient String netId;

    private transient User user;

    private transient User user2;

    private transient String roles;

    /**initialising objects to be called before every method.
     *
     */
    @BeforeEach
    public void initialize() {
        this.id = 123456;
        this.firstName = "John";
        this.lastName = "Doe";
        this.password = "passowrd";
        this.role = "admin";
        this.id2 = 988876;
        this.netId = "12345";
        this.user  = new User(id, firstName, lastName, password, role, netId);
        this.user2 = new User(id2, firstName, lastName, password, role, netId);
    }

    @Test
   public void constructorTest() {
        assertNotNull(user);
    }

    @Test
   public void getSetId() {
        user.setId(987603);
        assertEquals(987603, user.getId());
    }

    @Test
    public void getSetFirstNameTest() {
        user.setFirstName("Mike");
        assertEquals("Mike", user.getFirstName());
    }

    @Test
    public void getSetLastNameTest() {
        user.setLastName("Hunt");
        assertEquals("Hunt", user.getLastName());
    }

    @Test
    public void getSetPasswordTest() {
        user.setPassword("pwd");
        assertEquals("pwd", user.getPassword());
    }

    @Test
    public void getSetRole() {
        user.setRole("student");
        assertEquals("student", user.getRole());
    }

    @Test
    public void getSetNetId() {
        user.setNetId("dub");
        assertEquals("dub", user.getNetId());
    }



}
