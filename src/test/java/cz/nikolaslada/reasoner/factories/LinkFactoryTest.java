package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.repository.model.LinkModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.LinkDomain;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.validators.IsoValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for LinkFactory")
public class LinkFactoryTest {

    public IsoValidator i = new IsoValidator();
    public LinkFactory f = new LinkFactory(i);

    @DisplayName("Create list of LinkModel test")
    @Test
    void createModelListTest() {
        LinkDomain d = new LinkDomain(
                "en",
                "http://example.org/en/test",
                "EN Test"
        );

        LinkModel m = f.createModel(d);

        assertSame("en", m.getIso());
        assertSame("EN Test", m.getTitle());
        assertSame("http://example.org/en/test", m.getUrl());
    }

    @DisplayName("Create list of LinkModel exception test")
    @Test
    void createModelListExceptionTest() {
        String unknownIso = "XX";
        LinkDomain d = new LinkDomain(
                unknownIso,
                "http://example.org/xx/test",
                "XX Test"
        );

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            f.createModel(d);
        });

        String expected = IsoValidator.ERROR_MESSAGE_PATTERN;
        String actual = e.getMessage();

        assertTrue(actual.contains(expected));
        assertTrue(e.getErrorList().get(0).getData().get(0).contains(unknownIso));
    }

}
