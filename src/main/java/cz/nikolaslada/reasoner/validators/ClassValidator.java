package cz.nikolaslada.reasoner.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ClassValidator {

    private static final String NAME_ŔEGEX = "^[A-Z][a-zA-Z0-9_-]*$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_ŔEGEX);
    private static final int NAME_MAX_LENGTH = 255;

    public boolean isNameValid(String name) {
        return
                NAME_PATTERN.matcher(name).matches()
                && name.length() <= NAME_MAX_LENGTH;
    }

}
