package cz.nikolaslada.reasoner.rest.swagger.domains.response;

import cz.nikolaslada.reasoner.rest.swagger.domains.ExternalLink;
import cz.nikolaslada.reasoner.rest.swagger.domains.Translation;
import lombok.Getter;

import java.util.List;

@Getter
public class ClassDetail {

    private final Integer id;
    private final String name;
    private final String createdAt;
    private final String updatedAt;
    private final List<Translation> translationList;
    private final ExternalLink externalLink;


    public ClassDetail(
            Integer id,
            String name,
            String createdAt,
            String updatedAt,
            List<Translation> translationList,
            ExternalLink externalLink
    ) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.translationList = translationList;
        this.externalLink = externalLink;
    }

}
