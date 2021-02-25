package cz.nikolaslada.reasoner.rest.swagger.exceptions;

import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;

import java.util.List;

public class BadRequestException extends ErrorException {

    public BadRequestException(String message, List<ErrorItem> errorList) {
        super(message, errorList);
    }

}
