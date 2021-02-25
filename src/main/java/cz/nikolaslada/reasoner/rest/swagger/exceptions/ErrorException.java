package cz.nikolaslada.reasoner.rest.swagger.exceptions;

import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import lombok.Getter;

import java.util.List;

@Getter
public class ErrorException extends RuntimeException {

    private final String message;
    private final List<ErrorItem> errorList;

    public ErrorException(String message, List<ErrorItem> errorList) {
        this.message = message;
        this.errorList = errorList;
    }

}
