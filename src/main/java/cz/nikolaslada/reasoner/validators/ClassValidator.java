package cz.nikolaslada.reasoner.validators;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ClassValidator {

    private static final String NAME_ŔEGEX = "^[A-Z][a-zA-Z0-9_-]*$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_ŔEGEX);
    private static final int NAME_MAX_LENGTH = 255;

    public boolean isNameValid(String name) throws Exception {
        Integer length = name.length();

        if (length > NAME_MAX_LENGTH) {
            throw new Exception(
                    String.format(
                            "Length of class name must not be greater than %d! Current length is %d",
                            NAME_MAX_LENGTH,
                            length
                    )
            );
        }

        return NAME_PATTERN.matcher(name).matches();
    }

}
