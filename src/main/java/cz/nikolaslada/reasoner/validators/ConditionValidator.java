package cz.nikolaslada.reasoner.validators;

import org.springframework.stereotype.Component;

import static cz.nikolaslada.reasoner.repository.identifiers.OperatorId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.OperatorId.*;

@Component
public class ConditionValidator {

    public String getDbOperator(String id) throws Exception {
        switch (id) {
            case AND_API:
                return AND_DB;
            case OR_API:
                return OR_DB;
            default:
                throw new Exception(
                        String.format(
                                "Not supported id of operator '%c'.",
                                id
                        )
                );
        }
    }

    public String getApiOperator(String id) throws Exception {
        switch (id) {
            case AND_DB:
                return AND_API;
            case OR_DB:
                return OR_API;
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
