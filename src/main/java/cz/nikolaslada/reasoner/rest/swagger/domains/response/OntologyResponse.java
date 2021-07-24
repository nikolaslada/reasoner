package cz.nikolaslada.reasoner.rest.swagger.domains.response;

import cz.nikolaslada.reasoner.rest.swagger.domains.TranslationDomain;
import lombok.Getter;

import java.util.Date;
import java.util.List;

@Getter
public class OntologyResponse {

    private final String id;
    private final String name;
    private final List<TranslationDomain> translations;
    private final Integer classCount;
    private final Integer propertyCount;
    private final Integer individualCount;
    private final Date createdAt;
    private final String updatedAt;

    public OntologyResponse(String id, String name, List<TranslationDomain> translations, Integer classCount, Integer propertyCount, Integer individualCount, Date createdAt, String updatedAt) {
        super();
        this.id = id;
        this.name = name;
        this.translations = translations;
        this.classCount = classCount;
        this.propertyCount = propertyCount;
        this.individualCount = individualCount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
