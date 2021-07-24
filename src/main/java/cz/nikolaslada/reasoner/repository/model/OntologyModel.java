package cz.nikolaslada.reasoner.repository.model;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Document(collection = "ontologies")
public class OntologyModel {

    @MongoId
    private ObjectId id;

    private String name;
    private List<TranslationModel> translations;
    @Nullable
    private Integer classCount;
    @Nullable
    private Integer propertyCount;
    @Nullable
    private Integer individualCount;
    private ZonedDateTime updatedAt;


    public OntologyModel(
            ObjectId id,
            String name,
            List<TranslationModel> translations,
            Integer classCount,
            Integer propertyCount,
            Integer individualCount,
            ZonedDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.translations = translations;
        this.classCount = classCount;
        this.propertyCount = propertyCount;
        this.individualCount = individualCount;
        this.updatedAt = updatedAt;
    }

}
