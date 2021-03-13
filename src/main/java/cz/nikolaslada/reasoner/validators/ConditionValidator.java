package cz.nikolaslada.reasoner.validators;

import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.InternalException;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static cz.nikolaslada.reasoner.repository.identifiers.OperatorId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.OperatorId.*;

@Component
public class ConditionValidator {

    public String getDbOperator(String id) throws BadRequestException {
        switch (id) {
            case AND_API:
                return AND_DB;
            case OR_API:
                return OR_DB;
            default:
                throw new BadRequestException(
                        Arrays.asList(
                                new ErrorItem(
                                        "Not supported id of operator '%c'.",
                                        Arrays.asList(
                                                id
                                        )
                                )
                        )
                );
        }
    }

    public String getApiOperator(String id) throws InternalException {
        switch (id) {
            case AND_DB:
                return AND_API;
            case OR_DB:
                return OR_API;
            default:
                throw new InternalException(
                        "Not supported id of operator '%c' in database.",
                        Arrays.asList(
                                id
                        )
                );
        }
    }

}
