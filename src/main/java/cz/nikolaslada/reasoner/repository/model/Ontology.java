package cz.nikolaslada.reasoner.repository.model;

import com.mongodb.lang.Nullable;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.Translation;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Document(collection = "ontologies")
public class Ontology {

    @Id
    public String id;

    private String name;
    private List<Translation> translations;
    @Nullable
    private Integer classCount;
    @Nullable
    private Integer propertyCount;
    @Nullable
    private Integer individualCount;

    public Ontology() {
        super();
    }

    public Ontology(
            String name,
            List<Translation> translations,
            Integer classCount,
            Integer propertyCount,
            Integer individualCount
    ) {
        this.name = name;
        this.translations = translations;
        this.classCount = classCount;
        this.propertyCount = propertyCount;
        this.individualCount = individualCount;
    }

}
