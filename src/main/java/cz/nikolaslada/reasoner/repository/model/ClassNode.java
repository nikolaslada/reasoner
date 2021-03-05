package cz.nikolaslada.reasoner.repository.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.ZonedDateTime;

@Getter
@Document(collection = "classes")
public class ClassNode {

    public static final String SEQUENCE_NAME = ClassNode.class.getSimpleName() + "_seq";

    @MongoId
    private Integer id;
    private String name;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;


    public ClassNode(
            Integer id,
            String name,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt
    ) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
