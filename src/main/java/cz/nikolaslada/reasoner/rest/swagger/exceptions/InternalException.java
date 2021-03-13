package cz.nikolaslada.reasoner.rest.swagger.exceptions;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class InternalException extends ErrorException {

    private final String message;
    private final List<String> data;


    public InternalException(String message, List<String> data) {
        this.message = message;
        this.data = data;
    }

}
