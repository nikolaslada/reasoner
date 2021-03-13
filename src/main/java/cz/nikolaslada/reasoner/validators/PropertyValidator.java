package cz.nikolaslada.reasoner.validators;

import static cz.nikolaslada.reasoner.repository.identifiers.RestrictionId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.RestrictionId.*;

import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.InternalException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class PropertyValidator {

    private static final String NAME_ŔEGEX = "^[a-z][a-zA-Z0-9_-]*$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_ŔEGEX);
    private static final Integer NAME_MAX_LENGTH = 255;

    public boolean isNameValid(String name) throws BadRequestException {
        Integer length = name.length();

        if (length > NAME_MAX_LENGTH) {
            throw new BadRequestException(
                    Arrays.asList(
                            new ErrorItem(
                                    "Length of property name must not be greater than %d! Current length is %d",
                                    Arrays.asList(
                                            NAME_MAX_LENGTH.toString(),
                                            length.toString()
                                    )
                            )
                    )
            );
        }

        return NAME_PATTERN.matcher(name).matches();
    }

    public String getDbRestriction(String id) throws BadRequestException {
        if (id == null) {
            return null;
        }

        switch (id) {
            case SOME_API:
                return SOME_DB;
            case ONLY_API:
                return ONLY_DB;
            case HAS_VALUE_API:
                return HAS_VALUE_DB;
            case MIN_API:
                return MIN_DB;
            case MAX_API:
                return MAX_DB;
            case EQUAL_API:
                return EQUAL_DB;
            default:
                throw new BadRequestException(
                        Arrays.asList(
                                new ErrorItem(
                                        "Not supported id of restriction '%c'.",
                                        Arrays.asList(
                                                id
                                        )
                                )
                        )
                );
        }
    }

    public String getApiRestriction(String id) throws InternalException {
        if (id == null) {
            return null;
        }

        switch (id) {
            case SOME_DB:
                return SOME_API;
            case ONLY_DB:
                return ONLY_API;
            case HAS_VALUE_DB:
                return HAS_VALUE_API;
            case MIN_DB:
                return MIN_API;
            case MAX_DB:
                return MAX_API;
            case EQUAL_DB:
                return EQUAL_API;
            default:
                throw new InternalException(
                        "Not supported id of restriction '%c' in database.",
                        Arrays.asList(
                                id
                        )
                );
        }
    }

}
