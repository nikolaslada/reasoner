package cz.nikolaslada.reasoner.rest.swagger.domains.response;

import lombok.Getter;

import java.util.Date;

@Getter
public class UserDetail {

    private final String id;
    private final String login;
    private final String firstName;
    private final String surname;
    private final Date createdAt;
    private final String updatedAt;


    public UserDetail(String id, String login, String firstName, String surname, Date createdAt, String updatedAt) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.surname = surname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
