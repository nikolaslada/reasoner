package cz.nikolaslada.reasoner.validators;

import static cz.nikolaslada.reasoner.rest.swagger.identifiers.RestrictionId.*;

import cz.nikolaslada.reasoner.domains.Restriction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for PropertyValidator")
public class PropertyValidatorTest {

    public PropertyValidator p = new PropertyValidator();

    @DisplayName("Property name validation test")
    @Test
    void isNameValidTest() {
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
        assertFalse(p.isNameValid("psds5pBHwOFZwnCzbBvw56KITQDjTRuapanzVlPS0McdxlEeL1ShXyX7QlXjTU8G7WY4LdUk4jFok2A7vqxAYZrzB8uwYIx56iOrDrVHV7aoUj3D7YXmMHT9MSt3OuA9qXjEC8GbigDpyw6F1Udy3SPO1a5r1RLDrUgLhftsADicgWAXUcIYkpCyNzhxt7E2kYMR80Ch3EqL7tRIndchgpkRm6vEIRRrxvAmpU3ehvedxdfpzw5pBfSyxnVdBMQO"));
    }

    @DisplayName("Property get Restriction test")
    @Test
    void getRestrictionTest() throws Exception {
        assertEquals(p.getRestriction('s'), Restriction.SOME);
        assertEquals(p.getRestriction('o'), Restriction.ONLY);
        assertEquals(p.getRestriction('v'), Restriction.HAS_VALUE);
        assertEquals(p.getRestriction('i'), Restriction.MIN);
        assertEquals(p.getRestriction('a'), Restriction.MAX);
        assertEquals(p.getRestriction('e'), Restriction.EQUAL);

        assertEquals(p.getRestriction(SOME), Restriction.SOME);
        assertEquals(p.getRestriction(ONLY), Restriction.ONLY);
        assertEquals(p.getRestriction(HAS_VALUE), Restriction.HAS_VALUE);
        assertEquals(p.getRestriction(MIN), Restriction.MIN);
        assertEquals(p.getRestriction(MAX), Restriction.MAX);
        assertEquals(p.getRestriction(EQUAL), Restriction.EQUAL);
    }

    @DisplayName("Property get Restriction test, thrown exception")
    @Test
    void getRestrictionExceptionTest() {
        Exception e = assertThrows(
                Exception.class,
                () -> p.getRestriction(' '),
                "Expected getRestriction() to throw"
        );
        assertTrue(e.getMessage().contains("Not supported id of restriction ' '"));
    }

}
