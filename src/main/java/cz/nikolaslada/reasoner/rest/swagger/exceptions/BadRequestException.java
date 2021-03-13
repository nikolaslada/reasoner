package cz.nikolaslada.reasoner.rest.swagger.exceptions;

import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;

import java.util.List;
import lombok.Getter;

@Getter
public class BadRequestException extends ErrorException {

    private final List<ErrorItem> errorList;


    public BadRequestException(List<ErrorItem> errorList) {
        this.errorList = errorList;
    }

}
