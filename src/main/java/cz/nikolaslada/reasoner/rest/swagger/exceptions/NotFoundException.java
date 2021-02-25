package cz.nikolaslada.reasoner.rest.swagger.exceptions;

import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;

import java.util.List;

public class NotFoundException extends ErrorException {

    public NotFoundException(String message, List<ErrorItem> errorList) {
        super(message, errorList);
    }

}