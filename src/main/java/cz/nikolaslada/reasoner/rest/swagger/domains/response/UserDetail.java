package cz.nikolaslada.reasoner.rest.swagger.domains.response;

import lombok.Getter;

import java.util.Date;

@Getter
public class UserDetail {

    private final String id;
    private final String login;
    private final String firstName;
    private final String surname;
    private final String email;
    private final Date createdAt;
    private final String updatedAt;


    public UserDetail(String id, String login, String firstName, String surname, String email, Date createdAt, String updatedAt) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
