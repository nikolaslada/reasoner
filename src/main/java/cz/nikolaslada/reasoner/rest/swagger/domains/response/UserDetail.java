package cz.nikolaslada.reasoner.rest.swagger.domains.response;

import lombok.Getter;

@Getter
public class UserDetail {

    private final Integer id;
    private final String login;
    private final String firstName;
    private final String surname;
    private final String createdAt;
    private final String updatedAt;


    public UserDetail(Integer id, String login, String firstName, String surname, String createdAt, String updatedAt) {
        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.surname = surname;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

}
