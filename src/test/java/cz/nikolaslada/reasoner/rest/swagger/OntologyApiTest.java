package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.rest.swagger.domains.OntologyDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Unit tests for ontology")
@ExtendWith(MockitoExtension.class)
public class OntologyApiTest {

    @InjectMocks
    private OntologyApi ontologyApi;

    @DisplayName("Test get ontology")
    @Test
    void getOntologiesTest() {
        OntologyDetail response = ontologyApi.getOntology(1);

        assertEquals(response.getId(), 1);
        assertEquals(response.getName(), "Test ontology #1");
        assertEquals(response.getClassCount(), 256);
        assertEquals(response.getPropertyCount(), 32);
        assertEquals(response.getIndividualCount(), 0);
    }

}
