package cz.nikolaslada.reasoner.repository.model;

import lombok.Getter;

@Getter
public class TranslationModel {

    private final String iso;
    private final String name;
    private final String description;


    public TranslationModel(String iso, String name, String description) {
        this.iso = iso;
        this.name = name;
        this.description = description;
    }

}
