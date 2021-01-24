package cz.nikolaslada.reasoner.validators;

import static cz.nikolaslada.reasoner.rest.swagger.identifiers.RestrictionId.*;

import cz.nikolaslada.reasoner.domains.Restriction;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PropertyValidator {

    private static final String NAME_ŔEGEX = "^[a-z][a-zA-Z0-9_-]*$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_ŔEGEX);
    private static final int NAME_MAX_LENGTH = 255;

    public boolean isNameValid(String name) {
        return
                NAME_PATTERN.matcher(name).matches()
                && name.length() <= NAME_MAX_LENGTH;
    }

    public Restriction getRestriction(char id) throws Exception {
        switch (id) {
            case SOME:
                return Restriction.SOME;
            case ONLY:
                return Restriction.ONLY;
            case HAS_VALUE:
                return Restriction.HAS_VALUE;
            case MIN:
                return Restriction.MIN;
            case MAX:
                return Restriction.MAX;
            case EQUAL:
                return Restriction.EQUAL;
            default:
                throw new Exception("Not supported id of restriction '" + id + "'.");
        }
    }

}
