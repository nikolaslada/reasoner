package cz.nikolaslada.reasoner.rest.swagger.error;

import cz.nikolaslada.reasoner.rest.swagger.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({
            BadRequestException.class,
            ConflictException.class,
            GoneException.class,
            NotFoundException.class,
            InternalException.class
    })
    public ResponseEntity<ErrorResponse> customHandleBadRequest(ErrorException e, WebRequest request) throws ErrorException {
        HttpStatus status;
        List<ErrorItem> errorList;

        if (e instanceof BadRequestException) {
            status = HttpStatus.BAD_REQUEST;
            errorList = this.getListFromBadRequest((BadRequestException) e);
        } else if (e instanceof NotFoundException) {
            status = HttpStatus.NOT_FOUND;
            errorList = this.getListFromNotFound((NotFoundException) e);
        } else if (e instanceof ConflictException) {
            status = HttpStatus.CONFLICT;
            errorList = this.getListFromConflict((ConflictException) e);
        } else if (e instanceof GoneException) {
            status = HttpStatus.GONE;
            errorList = this.getListFromGone((GoneException) e);
        } else if (e instanceof InternalException) {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorList = this.getListFromInternal((InternalException) e);
        } else {
            throw e;
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                status.value(),
                errorList
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    private List<ErrorItem> getListFromBadRequest(BadRequestException e) {
        return e.getErrorList();
    }

    private List<ErrorItem> getListFromConflict(ConflictException e) {
        List<ErrorItem> list = new ArrayList<>();
        list.add(
                new ErrorItem(
                        e.getMessage(),
                        e.getData()
                )
        );
        return list;
    }

    private List<ErrorItem> getListFromGone(GoneException e) {
        List<ErrorItem> list = new ArrayList<>();
        list.add(
                new ErrorItem(
                        e.getMessage(),
                        e.getData()
                )
        );
        return list;
    }

    private List<ErrorItem> getListFromInternal(InternalException e) {
        List<ErrorItem> list = new ArrayList<>();
        list.add(
                new ErrorItem(
                        e.getMessage(),
                        e.getData()
                )
        );
        return list;
    }

    private List<ErrorItem> getListFromNotFound(NotFoundException e) {
        List<ErrorItem> list = new ArrayList<>();
        list.add(
                new ErrorItem(
                        e.getMessage(),
                        e.getData()
                )
        );
        return list;
    }

}
