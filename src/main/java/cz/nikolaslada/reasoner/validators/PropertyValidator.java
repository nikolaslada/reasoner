package cz.nikolaslada.reasoner.validators;

import static cz.nikolaslada.reasoner.repository.identifiers.RestrictionId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.RestrictionId.*;

import cz.nikolaslada.reasoner.rest.swagger.error.BadRequestBuilder;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.InternalException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Component
public class PropertyValidator {

    private static final String NAME_ŔEGEX = "^[a-z][a-zA-Z0-9_-]*$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_ŔEGEX);
    private static final Integer NAME_MAX_LENGTH = 255;

    private static final List<String> CLASS_RESTRICTION_LIST = Arrays.asList(
            SOME_DB,
            ONLY_DB,
            HAS_VALUE_API
    );

    private static final List<String> VALUE_RESTRICTION_LIST = Arrays.asList(
            MIN_DB,
            MAX_DB,
            EQUAL_DB
    );


    public boolean isNameValid(String name) throws BadRequestException {
        Integer length = name.length();

        if (length > NAME_MAX_LENGTH) {
            throw new BadRequestBuilder()
                    .addErrorItem(
                            "Length of property name must not be greater than %d! Current length is %d",
                            Arrays.asList(NAME_MAX_LENGTH.toString(), length.toString())
                    )
                    .build();
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
                throw new BadRequestBuilder()
                        .addErrorItem("Not supported id of restriction '%c'.", id)
                        .build();
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
                throw InternalException
                        .builder()
                        .message("Not supported id of restriction '%c' in database.")
                        .data(Arrays.asList(id))
                        .build();
        }
    }

    public Boolean isDbRestrictionForClass(String id) {
        return CLASS_RESTRICTION_LIST.contains(id);
    }

    public Boolean isDbRestrictionForValue(String id) {
        return VALUE_RESTRICTION_LIST.contains(id);
    }

}
