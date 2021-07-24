package cz.nikolaslada.reasoner.repository.model;

import lombok.Getter;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.ZonedDateTime;

@Getter
@Document(collection = "users")
public class UserModel {

    @MongoId
    private ObjectId id;

    private String login;
    private String hash;
    private String firstName;
    private String surname;
    private String email;
    private ZonedDateTime updatedAt;


    public UserModel(ObjectId id, String login, String hash, String firstName, String surname, String email, ZonedDateTime updatedAt) {
        this.id = id;
        this.login = login;
        this.hash = hash;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.updatedAt = updatedAt;
    }

}
