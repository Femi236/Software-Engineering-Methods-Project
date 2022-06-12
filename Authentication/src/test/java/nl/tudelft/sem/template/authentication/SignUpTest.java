package nl.tudelft.sem.template.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class SignUpTest {

    private transient SignUp signUp;

    private transient String netId;
    private transient String firstName;
    private transient  String lastName;
    private transient String password;
    private transient String role;

    /**.
     * initialiser method
     */
    @BeforeEach
    public void init() {
        this.netId = "1234";
        this.firstName = "John";
        this.lastName = "Doe";
        this.password = "password";
        this.role = "admin";
        signUp = new SignUp();

    }

    @Test
    public void testNetId() {
        signUp.setNetId(netId);
        assertEquals(signUp.getNetId(), "1234");

    }

    @Test
    public void testFirstName() {
        signUp.setFirstName(firstName);
        assertEquals(signUp.getFirstName(), "John");
    }

    @Test
    public void testLastName() {
        signUp.setLastName(lastName);
        assertEquals(signUp.getLastName(), "Doe");
    }

    @Test
    public void testPassword() {
        signUp.setPassword("password");
        assertEquals(signUp.getPassword(), "password");
    }

    @Test
    public void testRole() {
        signUp.setRole(role);
        assertEquals(signUp.getRole(), "admin");
    }

}
