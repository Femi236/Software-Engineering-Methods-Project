package nl.tudelft.sem.template.authentication;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class JwtConfigTest {
    private transient JwtConfig jwt;

    @BeforeEach
    public void beforeEach() {
        jwt = new JwtConfig();
    }

    @Test
    public void testUri() {
        jwt.setUri("hello");
        assertEquals(jwt.getUri(), "hello");
    }

    @Test
    public void testHeader() {
        jwt.setHeader("header");
        assertEquals(jwt.getHeader(), "header");
    }

    @Test
    public void testPrefix() {
        jwt.setPrefix("prefix");
        assertEquals(jwt.getPrefix(), "prefix");
    }

    @Test
    public void testExpiration() {
        jwt.setExpiration(12);
        assertEquals(jwt.getExpiration(), 12);
    }

    @Test
    public void testSecret() {
        jwt.setSecret("secret");
        assertEquals(jwt.getSecret(), "secret");
    }
}
