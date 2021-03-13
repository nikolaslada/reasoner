package cz.nikolaslada.reasoner.validators;

import static cz.nikolaslada.reasoner.repository.identifiers.RestrictionId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.RestrictionId.*;

import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.InternalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for PropertyValidator")
public class PropertyValidatorTest {

    public PropertyValidator p = new PropertyValidator();

    @DisplayName("Property name validation test")
    @Test
    void isNameValidTest() throws BadRequestException {
        assertTrue(p.isNameValid("property13"));
        assertTrue(p.isNameValid("property_Name"));
        assertTrue(p.isNameValid("a"));
        assertTrue(p.isNameValid("x_1"));
        assertTrue(p.isNameValid("property_2"));

        assertFalse(p.isNameValid(""));
        assertFalse(p.isNameValid("PropertyName"));
        assertFalse(p.isNameValid("_property"));
        assertFalse(p.isNameValid("1property"));
        assertFalse(p.isNameValid("property.Name"));
        assertFalse(p.isNameValid("p,"));
        assertFalse(p.isNameValid("p/"));
        assertFalse(p.isNameValid("p|"));
        assertFalse(p.isNameValid("p\\"));
        assertFalse(p.isNameValid("p\""));
        assertFalse(p.isNameValid("p'"));
        assertFalse(p.isNameValid("p@"));
        assertFalse(p.isNameValid("p&"));
        assertFalse(p.isNameValid("p<"));
        assertFalse(p.isNameValid("p>"));
        assertFalse(p.isNameValid("p;"));
    }

    @DisplayName("Property name validation test, thrown exception")
    @Test
    void isNameValidExceptionTest() {
        BadRequestException e = assertThrows(
                BadRequestException.class,
                () -> p.isNameValid("psds5pBHwOFZwnCzbBvw56KITQDjTRuapanzVlPS0McdxlEeL1ShXyX7QlXjTU8G7WY4LdUk4jFok2A7vqxAYZrzB8uwYIx56iOrDrVHV7aoUj3D7YXmMHT9MSt3OuA9qXjEC8GbigDpyw6F1Udy3SPO1a5r1RLDrUgLhftsADicgWAXUcIYkpCyNzhxt7E2kYMR80Ch3EqL7tRIndchgpkRm6vEIRRrxvAmpU3ehvedxdfpzw5pBfSyxnVdBMQO"),
                "Expected isNameValid() to throw"
        );
        ErrorItem errorItem = e.getErrorList().get(0);
        assertTrue(errorItem.getData().get(0).contains("255"));
        assertTrue(errorItem.getData().get(1).contains("256"));
    }

    @DisplayName("Property get Api Restriction test")
    @Test
    void getApiRestrictionTest() throws InternalException {
        assertEquals(p.getApiRestriction(null), null);
        assertEquals(p.getApiRestriction("s"), SOME_API);
        assertEquals(p.getApiRestriction("o"), ONLY_API);
        assertEquals(p.getApiRestriction("v"), HAS_VALUE_API);
        assertEquals(p.getApiRestriction("i"), MIN_API);
        assertEquals(p.getApiRestriction("a"), MAX_API);
        assertEquals(p.getApiRestriction("e"), EQUAL_API);

        assertEquals(p.getApiRestriction(SOME_DB), SOME_API);
        assertEquals(p.getApiRestriction(ONLY_DB), ONLY_API);
        assertEquals(p.getApiRestriction(HAS_VALUE_DB), HAS_VALUE_API);
        assertEquals(p.getApiRestriction(MIN_DB), MIN_API);
        assertEquals(p.getApiRestriction(MAX_DB), MAX_API);
        assertEquals(p.getApiRestriction(EQUAL_DB), EQUAL_API);
    }

    @DisplayName("Property get Api Restriction test, thrown exception")
    @Test
    void getApiRestrictionExceptionTest() {
        InternalException e = assertThrows(
                InternalException.class,
                () -> p.getApiRestriction(" "),
                "Expected getDbRestriction() to throw"
        );
        assertTrue(e.getData().get(0).contains(" "));
    }

    @DisplayName("Property get DB Restriction test")
    @Test
    void getDbRestrictionTest() throws BadRequestException {
        assertEquals(p.getDbRestriction(null), null);
        assertEquals(p.getDbRestriction("some"), SOME_DB);
        assertEquals(p.getDbRestriction("only"), ONLY_DB);
        assertEquals(p.getDbRestriction("hasValue"), HAS_VALUE_DB);
        assertEquals(p.getDbRestriction("min"), MIN_DB);
        assertEquals(p.getDbRestriction("max"), MAX_DB);
        assertEquals(p.getDbRestriction("equal"), EQUAL_DB);

        assertEquals(p.getDbRestriction(SOME_API), SOME_DB);
        assertEquals(p.getDbRestriction(ONLY_API), ONLY_DB);
        assertEquals(p.getDbRestriction(HAS_VALUE_API), HAS_VALUE_DB);
        assertEquals(p.getDbRestriction(MIN_API), MIN_DB);
        assertEquals(p.getDbRestriction(MAX_API), MAX_DB);
        assertEquals(p.getDbRestriction(EQUAL_API), EQUAL_DB);
    }

    @DisplayName("Property get DB Restriction test, thrown exception")
    @Test
    void getRestrictionExceptionTest() {
        BadRequestException e = assertThrows(
                BadRequestException.class,
                () -> p.getDbRestriction(" "),
                "Expected getDbRestriction() to throw"
        );
        assertTrue(e.getErrorList().get(0).getData().get(0).contains(" "));
    }

}
