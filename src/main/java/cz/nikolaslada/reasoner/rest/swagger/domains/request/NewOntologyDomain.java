package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import cz.nikolaslada.reasoner.rest.swagger.domains.TranslationDomain;
import lombok.Getter;

import java.util.List;

@Getter
public class NewOntologyDomain {

    private final String name;
    private final List<TranslationDomain> translationList;


    public NewOntologyDomain(String name, List<TranslationDomain> translationList) {
        super();
        this.name = name;
        this.translationList = translationList;
    }

}
