package cz.nikolaslada.reasoner.validators;

import cz.nikolaslada.reasoner.domains.ConditionType;
import cz.nikolaslada.reasoner.domains.Operator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static cz.nikolaslada.reasoner.rest.swagger.identifiers.ConditionTypeId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.OperatorId.*;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for ConditionValidator")
public class ConditionValidatorTest {

    public ConditionValidator c = new ConditionValidator();

    @DisplayName("Condition get ConditionType test")
    @Test
    void getConditionTypeTest() throws Exception {
        assertEquals(c.getConditionType('s'), ConditionType.SET);
        assertEquals(c.getConditionType('c'), ConditionType.CLASS);
        assertEquals(c.getConditionType('p'), ConditionType.PROPERTY);
        assertEquals(c.getConditionType('n'), ConditionType.NOT);

        assertEquals(c.getConditionType(SET), ConditionType.SET);
        assertEquals(c.getConditionType(CLASS), ConditionType.CLASS);
        assertEquals(c.getConditionType(PROPERTY), ConditionType.PROPERTY);
        assertEquals(c.getConditionType(TYPE_NOT), ConditionType.NOT);
    }

    @DisplayName("Condition get ConditionType test, thrown exception")
    @Test
    void getRestrictionExceptionTest() {
        Exception e = assertThrows(
                Exception.class,
                () -> c.getConditionType(' '),
                "Expected getConditionType() to throw"
        );
        assertTrue(e.getMessage().contains("Not supported id of condition type ' '."));
    }

    @DisplayName("Condition get Operator test")
    @Test
    void getOperatorTest() throws Exception {
        assertEquals(c.getOperator('a'), Operator.AND);
        assertEquals(c.getOperator('o'), Operator.OR);

        assertEquals(c.getOperator(AND), Operator.AND);
        assertEquals(c.getOperator(OR), Operator.OR);
    }

    @DisplayName("Condition get Operator test, thrown exception")
    @Test
    void getOperatorExceptionTest() {
        Exception e = assertThrows(
                Exception.class,
                () -> c.getOperator(' '),
                "Expected getOperator() to throw"
        );
        assertTrue(e.getMessage().contains("Not supported id of operator ' '."));
    }

}
