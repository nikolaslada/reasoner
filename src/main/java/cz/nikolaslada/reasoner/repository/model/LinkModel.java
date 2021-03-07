package cz.nikolaslada.reasoner.repository.model;

import lombok.Getter;

@Getter
public class LinkModel {

    private final String iso;
    private final String url;
    private final String title;


    public LinkModel(String iso, String url, String title) {
        this.iso = iso;
        this.url = url;
        this.title = title;
    }

}
