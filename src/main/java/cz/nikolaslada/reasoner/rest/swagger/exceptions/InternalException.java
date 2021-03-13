package cz.nikolaslada.reasoner.rest.swagger.exceptions;

import lombok.Getter;

import java.util.List;

@Getter
public class InternalException extends ErrorException {

    private final String message;
    private final List<String> data;


    public InternalException(String message, List<String> data) {
        this.message = message;
        this.data = data;
    }

}
