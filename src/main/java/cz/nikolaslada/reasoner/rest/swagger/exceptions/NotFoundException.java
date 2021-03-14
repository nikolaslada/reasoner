package cz.nikolaslada.reasoner.rest.swagger.exceptions;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class NotFoundException extends ErrorException {

    private final String message;
    private final List<String> data;


    public NotFoundException(String message, List<String> data) {
        this.message = message;
        this.data = data;
    }

}
