package cz.nikolaslada.reasoner.validators;

import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.regex.Pattern;

@Component
public class ClassValidator {

    private static final String NAME_ŔEGEX = "^[A-Z][a-zA-Z0-9_-]*$";
    private static final Pattern NAME_PATTERN = Pattern.compile(NAME_ŔEGEX);
    private static final Integer NAME_MAX_LENGTH = 255;

    public boolean isNameValid(String name) throws BadRequestException {
        Integer length = name.length();

        if (length > NAME_MAX_LENGTH) {
            throw new BadRequestException(
                    Arrays.asList(
                            new ErrorItem(
                                    "Length of class name must not be greater than %d! Current length is %d",
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

}
