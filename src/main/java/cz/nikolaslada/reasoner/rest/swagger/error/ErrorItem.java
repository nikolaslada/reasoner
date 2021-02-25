package cz.nikolaslada.reasoner.rest.swagger.error;

import lombok.Getter;

import java.util.List;

@Getter
public class ErrorItem {

    private final String message;
    private final List<String> data;

    public ErrorItem(String message, List<String> data) {
        this.message = message;
        this.data = data;
    }

}
