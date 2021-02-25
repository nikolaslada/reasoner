package cz.nikolaslada.reasoner.validators;

import cz.nikolaslada.reasoner.rest.swagger.domains.request.Translation;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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

    @DisplayName("Check List of Translation exception test")
    @Test
    void checkIsoListExceptionTest() {
        String unknownIso = "XX";
        List<Translation> translationList = Arrays.asList(
                new Translation(
                        unknownIso,
                        "Test",
                        "Test"
                )
        );

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            i.checkIsoList(translationList);
        });

        String expected = "Not supported Language ISO codes: ";
        String actual = e.getMessage();

        assertTrue(actual.contains(expected));
        assertTrue(e.getErrorList().get(0).getData().get(0).contains(unknownIso));
    }

}
