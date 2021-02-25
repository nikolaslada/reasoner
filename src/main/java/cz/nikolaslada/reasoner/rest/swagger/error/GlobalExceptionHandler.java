package cz.nikolaslada.reasoner.rest.swagger.error;

import cz.nikolaslada.reasoner.rest.swagger.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            BadRequestException.class,
            ConflictException.class,
            GoneException.class,
            NotFoundException.class
    })
    public ResponseEntity<ErrorResponse> customHandleBadRequest(ErrorException e, WebRequest request) throws ErrorException {
        HttpStatus status;

        if (e instanceof BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
        } else if (e instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
        } else if (e instanceof ConflictException) {
            status = HttpStatus.CONFLICT;
        } else if (e instanceof GoneException) {
            status = HttpStatus.GONE;
        } else {
            throw e;
        }

        List<ErrorItem> errorList = e.getErrorList();
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                errorList.size() != 1 ? "Bad request" : errorList.get(0).getMessage(),
                errorList
        );

        return new ResponseEntity<>(errorResponse, status);
    }

}
