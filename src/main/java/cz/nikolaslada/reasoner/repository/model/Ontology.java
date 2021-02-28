package cz.nikolaslada.reasoner.repository.model;

import com.mongodb.lang.Nullable;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.Translation;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Document(collection = "ontologies")
public class Ontology {

    public static final String SEQUENCE_NAME = Ontology.class.getSimpleName() + "_seq";

    @MongoId
    private Integer id;

    private String name;
    private List<Translation> translations;
    @Nullable
    private Integer classCount;
    @Nullable
    private Integer propertyCount;
    @Nullable
    private Integer individualCount;


    public Ontology(
            Integer id,
            String name,
            List<Translation> translations,
            Integer classCount,
            Integer propertyCount,
            Integer individualCount
    ) {
        this.id = id;
        this.name = name;
        this.translations = translations;
        this.classCount = classCount;
        this.propertyCount = propertyCount;
        this.individualCount = individualCount;
    }

}
