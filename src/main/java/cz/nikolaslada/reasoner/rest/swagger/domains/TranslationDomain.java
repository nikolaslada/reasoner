package cz.nikolaslada.reasoner.rest.swagger.domains;

import lombok.Getter;

import javax.validation.constraints.Size;

@Getter
public class TranslationDomain {

    @Size(min = 2, max = 3, message = "iso must be 2 characters long")
    private final String iso;

    @Size(min = 1, max = 255, message = "name must be between 1 and 255 characters")
    private final String name;

    @Size(min = 0, max = 65535, message = "description must be a maximum of 65,535 characters long")
    private final String description;


    public TranslationDomain(String iso, String name, String description) {
        super();
        this.iso = iso;
        this.name = name;
        this.description = description;
    }

}
