package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import cz.nikolaslada.reasoner.rest.swagger.domains.TranslationDomain;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class NewOntologyRequest {

    @NotBlank(message = "name cannot be blank")
    @Size(min = 1, max = 255, message = "name must be between 1 and 255 characters")
    private final String name;
    private final List<TranslationDomain> translationList;


    public NewOntologyRequest(String name, List<TranslationDomain> translationList) {
        super();
        this.name = name;
        this.translationList = translationList;
    }

}
