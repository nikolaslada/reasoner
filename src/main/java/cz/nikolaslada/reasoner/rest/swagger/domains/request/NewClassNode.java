package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import cz.nikolaslada.reasoner.rest.swagger.domains.ExternalLink;
import cz.nikolaslada.reasoner.rest.swagger.domains.Translation;
import lombok.Getter;
import java.util.List;

@Getter
public class NewClassNode {

    private final String name;
    private final List<Translation> translationList;
    private final ExternalLink externalLink;
    private final List<Definition> definitionList;
    private final Condition condition;


    public NewClassNode(
            String name,
            List<Translation> translationList,
            ExternalLink externalLink,
            List<Definition> definitionList,
            Condition condition
    ) {
        super();
        this.name = name;
        this.translationList = translationList;
        this.externalLink = externalLink;
        this.definitionList = definitionList;
        this.condition = condition;
    }

}
