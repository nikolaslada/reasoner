package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.repository.model.TranslationModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.TranslationDomain;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.validators.IsoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for TranslationFactory")
public class TranslationFactoryTest {

    public IsoValidator i = new IsoValidator();
    public TranslationFactory f = new TranslationFactory(i);

    @DisplayName("Create list of TranslationDetail test")
    @Test
    void createDetailListTest() {
        List<TranslationModel> m = Arrays.asList(
                new TranslationModel(
                        "en",
                        "TestEN",
                        "TestEN"
                ),
                new TranslationModel(
                        "cs",
                        "TestCS",
                        "TestCS"
                )
        );

        List<TranslationDomain> d = f.createDomainList(m);

        assertSame(2, d.size());
        assertSame("TestEN", d.get(0).getName());
        assertSame("en", d.get(0).getIso());
        assertSame("TestCS", d.get(1).getDescription());
    }

    @DisplayName("Create list of TranslationModel test")
    @Test
    void createModelListTest() {
        List<TranslationDomain> d = Arrays.asList(
                new TranslationDomain(
                        "en",
                        "TestEN",
                        "TestEN"
                ),
                new TranslationDomain(
                        "cs",
                        "TestCS",
                        "TestCS"
                )
        );

        List<TranslationModel> m = f.createModelList(d);

        assertSame(2, m.size());
        assertSame("TestEN", m.get(0).getName());
        assertSame("en", m.get(0).getIso());
        assertSame("TestCS", m.get(1).getDescription());
    }

    @DisplayName("Create list of TranslationModel exception test")
    @Test
    void createModelListExceptionTest() {
        String unknownIso = "XX";
        List<TranslationDomain> translationList = Arrays.asList(
                new TranslationDomain(
                        unknownIso,
                        "Test",
                        "Test"
                )
        );

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            f.createModelList(translationList);
        });

        String expected = IsoValidator.ERROR_MESSAGE_PATTERN;
        String actual = e.getMessage();

        assertTrue(actual.contains(expected));
        assertTrue(e.getErrorList().get(0).getData().get(0).contains(unknownIso));
    }

}
