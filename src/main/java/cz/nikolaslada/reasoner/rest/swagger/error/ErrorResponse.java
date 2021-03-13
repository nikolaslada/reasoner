package cz.nikolaslada.reasoner.rest.swagger.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private final LocalDateTime timestamp;
    private final int status;
    private final List<ErrorItem> errorList;

    public ErrorResponse(LocalDateTime timestamp, int status, List<ErrorItem> errorList) {
        this.timestamp = timestamp;
        this.status = status;
        this.errorList = errorList;
    }

}
