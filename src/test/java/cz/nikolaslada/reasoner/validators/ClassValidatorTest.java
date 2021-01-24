package cz.nikolaslada.reasoner.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for ClassValidator")
public class ClassValidatorTest {

    public ClassValidator c = new ClassValidator();

    @DisplayName("Class name validation test")
    @Test
    void isNameValidTest() throws Exception {
        assertTrue(c.isNameValid("Class13"));
        assertTrue(c.isNameValid("Class_Name"));
        assertTrue(c.isNameValid("A"));
        assertTrue(c.isNameValid("X_1"));
        assertTrue(c.isNameValid("Class_2"));

        assertFalse(c.isNameValid(""));
        assertFalse(c.isNameValid("className"));
        assertFalse(c.isNameValid("_Class"));
        assertFalse(c.isNameValid("1Class"));
        assertFalse(c.isNameValid("Class.Name"));
        assertFalse(c.isNameValid("C,"));
        assertFalse(c.isNameValid("C/"));
        assertFalse(c.isNameValid("C|"));
        assertFalse(c.isNameValid("C\\"));
        assertFalse(c.isNameValid("C\""));
        assertFalse(c.isNameValid("C'"));
        assertFalse(c.isNameValid("C@"));
        assertFalse(c.isNameValid("C&"));
        assertFalse(c.isNameValid("C<"));
        assertFalse(c.isNameValid("C>"));
        assertFalse(c.isNameValid("C;"));
    }

    @DisplayName("Class name validation test, thrown exception")
    @Test
    void isNameValidExceptionTest() {
        Exception e = assertThrows(
                Exception.class,
                () -> c.isNameValid("Csds5pBHwOFZwnCzbBvw56KITQDjTRuapanzVlPS0McdxlEeL1ShXyX7QlXjTU8G7WY4LdUk4jFok2A7vqxAYZrzB8uwYIx56iOrDrVHV7aoUj3D7YXmMHT9MSt3OuA9qXjEC8GbigDpyw6F1Udy3SPO1a5r1RLDrUgLhftsADicgWAXUcIYkpCyNzhxt7E2kYMR80Ch3EqL7tRIndchgpkRm6vEIRRrxvAmpU3ehvedxdfpzw5pBfSyxnVdBMQO"),
                "Expected isNameValid() to throw"
        );
        assertTrue(e.getMessage().contains("Length of class name must not be greater than 255! Current length is 256"));
    }

}
