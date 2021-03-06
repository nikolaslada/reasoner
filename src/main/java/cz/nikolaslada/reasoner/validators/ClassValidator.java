package cz.nikolaslada.reasoner.validators;

import cz.nikolaslada.reasoner.rest.swagger.error.BadRequestBuilder;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.InternalException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Pattern;

import static cz.nikolaslada.reasoner.repository.identifiers.OperatorId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.OperatorId.*;

@Component
public class ClassValidator {

    private static final String NAME_ŔEGEX = "^[A-Z][a-zA-Z0-9_-]*$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_ŔEGEX);
    private static final Integer NAME_MAX_LENGTH = 255;

    public boolean isNameValid(String name) throws BadRequestException {
        Integer length = name.length();

        if (length > NAME_MAX_LENGTH) {
            throw new BadRequestBuilder()
                    .addErrorItem(
                            "Length of class name must not be greater than %d! Current length is %d",
                            Arrays.asList(NAME_MAX_LENGTH.toString(), length.toString())
                    )
                    .build();
        }

        return NAME_PATTERN.matcher(name).matches();
    }

    public String getDbOperator(String id) throws BadRequestException {
        switch (id) {
            case AND_API:
                return AND_DB;
            case OR_API:
                return OR_DB;
            default:
                throw new BadRequestBuilder()
                        .addErrorItem("Not supported id of operator '%c'.", id)
                        .build();
        }
    }

    public String getApiOperator(String id) throws InternalException {
        switch (id) {
            case AND_DB:
                return AND_API;
            case OR_DB:
                return OR_API;
            default:
                throw InternalException
                        .builder()
                        .message("Not supported id of operator '%c' in database.")
                        .data(Arrays.asList(id))
                        .build();
        }
    }

}
