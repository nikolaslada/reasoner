package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.rest.swagger.domains.ClassTree;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Unit tests for class tree")
@ExtendWith(MockitoExtension.class)
public class ClassTreeApiTest {

    @InjectMocks
    private ClassTreeApi classTreeApi;

    @DisplayName("Test get class tree")
    @Test
    void getClassTreeTest() {
        ClassTree response = classTreeApi.getClassTree(1);

        assertEquals(response.getCount(), 12);

        assertEquals(response.getTreeList().getId(), 1);
        assertEquals(response.getTreeList().getName(), "owl:Thing");
        assertEquals(response.getTreeList().getNodes().size(), 4);
    }

}
