package cz.nikolaslada.reasoner.rest.swagger.domains;

import lombok.Getter;

@Getter
public class LinkDomain {

    private final String iso;
    private final String url;
    private final String title;


    public LinkDomain(String iso, String url, String title) {
        super();
        this.iso = iso;
        this.url = url;
        this.title = title;
    }

}
