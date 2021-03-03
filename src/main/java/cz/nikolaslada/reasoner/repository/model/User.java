package cz.nikolaslada.reasoner.repository.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

@Getter
@Document(collection = "users")
public class User {

    public static final String SEQUENCE_NAME = User.class.getSimpleName() + "_seq";

    @MongoId
    private Integer id;

    private String login;
    private String hash;
    private String firstName;
    private String surname;
    private Date createdAt;
    private Date updatedAt;


    public User(Integer id, String login, String hash, String firstName, String surname, Date createdAt, Date updatedAt) {
        this.id = id;
        this.login = login;
        this.hash = hash;
        this.firstName = firstName;
        this.surname = surname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
