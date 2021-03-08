package cz.nikolaslada.reasoner.repository.model;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Getter
@Document(collection = "properties")
public class Property {

    public static final String SEQUENCE_NAME = Property.class.getSimpleName() + "_seq";

    @MongoId
    private Integer id;
    @DBRef
    private Integer ontologyId;
    private String name;
    @Nullable
    private List<TranslationModel> translations;
    @Nullable
    private LinkModel link;


    public Property(
            Integer id,
            Integer ontologyId,
            String name,
            List<TranslationModel> translations,
            LinkModel link
    ) {
        this.id = id;
        this.ontologyId = ontologyId;
        this.name = name;
        this.translations = translations;
        this.link = link;
    }

}
