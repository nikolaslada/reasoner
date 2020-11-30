package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.rest.swagger.domains.MyOntologies;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Unit tests for my-ontologies")
@ExtendWith(MockitoExtension.class)
public class MyOntologiesApiTest {

    @InjectMocks
    private MyOntologiesApi myOntologiesApi;

    @DisplayName("Test get my-ontologies")
    @Test
    void getMyOntologiesTest() {
        MyOntologies response = myOntologiesApi.getMyOntologies();

        assertEquals(response.getCount(), 3);
        assertEquals(response.getOffset(), 10);
        assertEquals(response.getTotalCount(), 13);

        assertEquals(response.getOntologyList().size(), 3);

        assertEquals(response.getOntologyList().get(0).getId(), 1);
        assertEquals(response.getOntologyList().get(0).getName(), "Test ontology #1");

        assertEquals(response.getOntologyList().get(1).getClassCount(), 2147483647);
        assertEquals(response.getOntologyList().get(1).getPropertyCount(), 32768);
        assertEquals(response.getOntologyList().get(1).getIndividualCount(), 16777216);
    }

}
