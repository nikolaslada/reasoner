package cz.nikolaslada.reasoner.validators;

import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.InternalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cz.nikolaslada.reasoner.repository.identifiers.OperatorId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.OperatorId.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for ConditionValidator")
public class ConditionValidatorTest {

    public ConditionValidator c = new ConditionValidator();

    @DisplayName("Condition get API Operator test")
    @Test
    void getApiOperatorTest() throws InternalException {
        assertEquals(c.getApiOperator("a"), AND_API);
        assertEquals(c.getApiOperator("o"), OR_API);

        assertEquals(c.getApiOperator(AND_DB), AND_API);
        assertEquals(c.getApiOperator(OR_DB), OR_API);
    }

    @DisplayName("Condition get API Operator test, thrown exception")
    @Test
    void getApiOperatorExceptionTest() {
        InternalException e = assertThrows(
                InternalException.class,
                () -> c.getApiOperator(" "),
                "Expected getApiOperator() to throw"
        );
        assertTrue(e.getData().get(0).contains(" "));
    }

    @DisplayName("Condition get DB Operator test")
    @Test
    void getDbOperatorTest() throws BadRequestException {
        assertEquals(c.getDbOperator("and"), AND_DB);
        assertEquals(c.getDbOperator("or"), OR_DB);

        assertEquals(c.getDbOperator(AND_API), AND_DB);
        assertEquals(c.getDbOperator(OR_API), OR_DB);
    }

    @DisplayName("Condition get DB Operator test, thrown exception")
    @Test
    void getDbOperatorExceptionTest() {
        BadRequestException e = assertThrows(
                BadRequestException.class,
                () -> c.getDbOperator(" "),
                "Expected getDbOperator() to throw"
        );
        assertTrue(e.getErrorList().get(0).getData().get(0).contains(" "));
    }

}
