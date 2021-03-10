package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.repository.model.LinkModel;
import cz.nikolaslada.reasoner.repository.model.TranslationModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.LinkDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.TranslationDomain;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.validators.IsoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for TranslationFactory")
public class SharedFactoryTest {

    public IsoValidator i = new IsoValidator();
    public SharedFactory f = new SharedFactory(i);

    @DisplayName("Create of LinkModel test")
    @Test
    void createLinkModelTest() {
        LinkDomain d = new LinkDomain(
                "en",
                "http://example.org/en/test",
                "EN Test"
        );

        LinkModel m = f.createLinkModel(d);

        assertSame("en", m.getIso());
        assertSame("EN Test", m.getTitle());
        assertSame("http://example.org/en/test", m.getUrl());
    }

    @DisplayName("Create of LinkModel exception test")
    @Test
    void createLinkModelExceptionTest() {
        String unknownIso = "XX";
        LinkDomain d = new LinkDomain(
                unknownIso,
                "http://example.org/xx/test",
                "XX Test"
        );

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            f.createLinkModel(d);
        });

        String expected = IsoValidator.ERROR_MESSAGE_PATTERN;
        String actual = e.getMessage();

        assertTrue(actual.contains(expected));
        assertTrue(e.getErrorList().get(0).getData().get(0).contains(unknownIso));
    }

    @DisplayName("Create list of TranslationDomain test")
    @Test
    void createTranslationDomainListTest() {
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

        List<TranslationDomain> d = f.createTranslationDomainList(m);

        assertSame(2, d.size());
        assertSame("TestEN", d.get(0).getName());
        assertSame("en", d.get(0).getIso());
        assertSame("TestCS", d.get(1).getDescription());
    }

    @DisplayName("Create list of TranslationModel test")
    @Test
    void createTranslationModelListTest() {
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

        List<TranslationModel> m = f.createTranslationModelList(d);

        assertSame(2, m.size());
        assertSame("TestEN", m.get(0).getName());
        assertSame("en", m.get(0).getIso());
        assertSame("TestCS", m.get(1).getDescription());
    }

    @DisplayName("Create list of TranslationModel exception test")
    @Test
    void createTranslationModelListExceptionTest() {
        String unknownIso = "XX";
        List<TranslationDomain> translationList = Arrays.asList(
                new TranslationDomain(
                        unknownIso,
                        "Test",
                        "Test"
                )
        );

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            f.createTranslationModelList(translationList);
        });

        String expected = IsoValidator.ERROR_MESSAGE_PATTERN;
        String actual = e.getMessage();

        assertTrue(actual.contains(expected));
        assertTrue(e.getErrorList().get(0).getData().get(0).contains(unknownIso));
    }

}
