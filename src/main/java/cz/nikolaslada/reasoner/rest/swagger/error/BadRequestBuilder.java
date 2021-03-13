package cz.nikolaslada.reasoner.rest.swagger.error;

import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BadRequestBuilder {

    private final List<ErrorItem> errorItemList;

    public BadRequestBuilder() {
        this.errorItemList = new ArrayList<>();
    }

    public BadRequestBuilder addErrorItem(String message) {
        this.errorItemList.add(
                new ErrorItem(
                        message,
                        new ArrayList<>()
                )
        );
        return this;
    }

    public BadRequestBuilder addErrorItem(String message, List<String> data) {
        this.errorItemList.add(
                new ErrorItem(
                        message,
                        data
                )
        );
        return this;
    }

    public BadRequestBuilder addErrorItem(String message, String data) {
        this.errorItemList.add(
                new ErrorItem(
                        message,
                        Arrays.asList(
                                data
                        )
                )
        );
        return this;
    }

    public Boolean isEmpty() {
        return this.errorItemList.isEmpty();
    }

    public BadRequestException build() {
        return new BadRequestException(this.errorItemList);
    }

}
