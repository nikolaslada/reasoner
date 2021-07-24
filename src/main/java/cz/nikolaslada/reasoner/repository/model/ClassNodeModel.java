package cz.nikolaslada.reasoner.repository.model;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.ZonedDateTime;
import java.util.List;

@Getter
@Document(collection = "classes")
public class ClassNodeModel {

    public static final String SEQUENCE_NAME = ClassNodeModel.class.getSimpleName() + "_seq";

    @MongoId
    private Integer id;
    @DBRef
    private ObjectId ontologyId;
    private String name;
    private ZonedDateTime createdAt;
    @Nullable
    private ZonedDateTime updatedAt;
    @Nullable
    private List<TranslationModel> translations;
    @Nullable
    private LinkModel link;
    @Nullable
    private List<DefinitionModel> definitions;
    @Nullable
    private ConditionModel condition;


    public ClassNodeModel(
            Integer id,
            ObjectId ontologyId,
            String name,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt,
            List<TranslationModel> translations,
            LinkModel link,
            List<DefinitionModel> definitions,
            ConditionModel condition
    ) {
        this.id = id;
        this.ontologyId = ontologyId;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.translations = translations;
        this.link = link;
        this.definitions = definitions;
        this.condition = condition;
    }

}
