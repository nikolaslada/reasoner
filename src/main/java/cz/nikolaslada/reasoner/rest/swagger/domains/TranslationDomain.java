package cz.nikolaslada.reasoner.rest.swagger.domains;

import lombok.Getter;

@Getter
public class TranslationDomain {

    private final String iso;
    private final String name;
    private final String description;


    public TranslationDomain(String iso, String name, String description) {
        super();
        this.iso = iso;
        this.name = name;
        this.description = description;
    }

}
