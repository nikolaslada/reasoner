package cz.nikolaslada.reasoner.rest.swagger.exceptions;

import java.util.List;
import lombok.Getter;

@Getter
public class ConflictException extends ErrorException {

    private final String message;
    private final List<String> data;


    public ConflictException(String message, List<String> data) {
        this.message = message;
        this.data = data;
    }

}
