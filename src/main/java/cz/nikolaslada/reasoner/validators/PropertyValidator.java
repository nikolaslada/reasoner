package cz.nikolaslada.reasoner.validators;

import static cz.nikolaslada.reasoner.repository.identifiers.RestrictionId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.RestrictionId.*;

import cz.nikolaslada.reasoner.domains.Restriction;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PropertyValidator {

    private static final String NAME_ŔEGEX = "^[a-z][a-zA-Z0-9_-]*$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_ŔEGEX);
    private static final int NAME_MAX_LENGTH = 255;

    public boolean isNameValid(String name) throws Exception {
        Integer length = name.length();

        if (length > NAME_MAX_LENGTH) {
            throw new Exception(
                    String.format(
                            "Length of property name must not be greater than %d! Current length is %d",
                            NAME_MAX_LENGTH,
                            length
                    )
            );
        }

        return NAME_PATTERN.matcher(name).matches();
    }

    public String getDbRestriction(String id) throws Exception {
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
                throw new Exception(
                        String.format(
                                "Not supported id of restriction '%c'.",
                                id
                        )
                );
        }
    }

    public String getApiRestriction(String id) throws Exception {
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
                return null;
        }
    }

}
