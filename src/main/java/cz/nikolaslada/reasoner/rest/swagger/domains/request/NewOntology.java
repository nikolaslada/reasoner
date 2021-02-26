package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import lombok.Getter;

import java.util.List;

@Getter
public class NewOntology {

    private final String name;
    private final List<Translation> translationList;

    public NewOntology(String name, List<Translation> translationList) {
        super();
        this.name = name;
        this.translationList = translationList;
    }

}
