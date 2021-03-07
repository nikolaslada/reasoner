package cz.nikolaslada.reasoner.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for IsoValidator")
public class IsoValidatorTest {

    public IsoValidator i = new IsoValidator();

    @DisplayName("Language ISO validation test")
    @Test
    void isIsoLanguageValidTest() {
        assertTrue(i.isIsoLanguageValid("en"));
        assertTrue(i.isIsoLanguageValid("cs"));
        assertTrue(i.isIsoLanguageValid("sk"));
        assertTrue(i.isIsoLanguageValid("pl"));

        assertFalse(i.isIsoLanguageValid("EN"));
        assertFalse(i.isIsoLanguageValid("cz"));
        assertFalse(i.isIsoLanguageValid("xx"));
        assertFalse(i.isIsoLanguageValid(""));
    }

}
