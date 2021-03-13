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

    public void addErrorItem(String message) {
        this.errorItemList.add(
                new ErrorItem(
                        message,
                        new ArrayList<>()
                )
        );
    }

    public void addErrorItem(String message, List<String> data) {
        this.errorItemList.add(
                new ErrorItem(
                        message,
                        data
                )
        );
    }

    public void addErrorItem(String message, String data) {
        this.errorItemList.add(
                new ErrorItem(
                        message,
                        Arrays.asList(
                                data
                        )
                )
        );
    }

    public Boolean isEmpty() {
        return this.errorItemList.isEmpty();
    }

    public BadRequestException build() {
        return new BadRequestException(this.errorItemList);
    }

}
