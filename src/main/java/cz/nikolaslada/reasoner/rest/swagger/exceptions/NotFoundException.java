package cz.nikolaslada.reasoner.rest.swagger.exceptions;

import java.util.List;
import lombok.Getter;

@Getter
public class NotFoundException extends ErrorException {

    private final String message;
    private final List<String> data;


    public NotFoundException(String message, List<String> data) {
        this.message = message;
        this.data = data;
    }

}
