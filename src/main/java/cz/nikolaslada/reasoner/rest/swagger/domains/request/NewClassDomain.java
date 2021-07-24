package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import cz.nikolaslada.reasoner.rest.swagger.domains.ConditionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.DefinitionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.LinkDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.TranslationDomain;
import lombok.Getter;
import java.util.List;

@Getter
public class NewClassDomain {

    private final String name;
    private final String ontologyId;
    private final List<TranslationDomain> translationList;
    private final LinkDomain linkDomain;
    private final List<DefinitionDomain> definitionList;
    private final ConditionDomain condition;


    public NewClassDomain(
            String name,
            String ontologyId,
            List<TranslationDomain> translationList,
            LinkDomain linkDomain,
            List<DefinitionDomain> definitionList,
            ConditionDomain condition
    ) {
        super();
        this.name = name;
        this.ontologyId = ontologyId;
        this.translationList = translationList;
        this.linkDomain = linkDomain;
        this.definitionList = definitionList;
        this.condition = condition;
    }

}
