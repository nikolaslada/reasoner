package cz.nikolaslada.reasoner.validators;

import cz.nikolaslada.reasoner.domains.ConditionType;
import cz.nikolaslada.reasoner.domains.Operator;
import org.springframework.stereotype.Component;

import static cz.nikolaslada.reasoner.rest.swagger.identifiers.ConditionTypeId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.OperatorId.*;

@Component
public class ConditionValidator {

    public ConditionType getConditionType(char id) throws Exception {
        switch (id) {
            case SET:
                return ConditionType.SET;
            case CLASS:
                return ConditionType.CLASS;
            case PROPERTY:
                return ConditionType.PROPERTY;
            case TYPE_NOT:
                return ConditionType.NOT;
            default:
                throw new Exception(
                        String.format(
                                "Not supported id of condition type '%c'.",
                                id
                        )
                );
        }
    }

    public Operator getOperator(char id) throws Exception {
        switch (id) {
            case AND:
                return Operator.AND;
            case OR:
                return Operator.OR;
            default:
                throw new Exception(
                        String.format(
                                "Not supported id of operator '%c'.",
                                id
                        )
                );
        }
    }

}
