package cz.nikolaslada.reasoner.rest.swagger.domains.response;

import cz.nikolaslada.reasoner.rest.swagger.domains.ConditionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.DefinitionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.LinkDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.TranslationDomain;
import lombok.Getter;

import java.util.List;

@Getter
public class ClassDetail {

    private final Integer id;
    private final Integer ontologyId;
    private final String name;
    private final String createdAt;
    private final String updatedAt;
    private final List<TranslationDomain> translationList;
    private final LinkDomain linkDomain;
    private final List<DefinitionDomain> definitions;
    private final ConditionDomain condition;


    public ClassDetail(
            Integer id,
            Integer ontologyId,
            String name,
            String createdAt,
            String updatedAt,
            List<TranslationDomain> translationList,
            LinkDomain linkDomain,
            List<DefinitionDomain> definitions,
            ConditionDomain condition) {
        this.id = id;
        this.ontologyId = ontologyId;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.translationList = translationList;
        this.linkDomain = linkDomain;
        this.definitions = definitions;
        this.condition = condition;
    }

}
