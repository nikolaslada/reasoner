package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import lombok.Getter;

@Getter
public class NewUser {

    private final String firstName;
    private final String surname;
    private final String login;
    private final String password;


    public NewUser(String firstName, String surname, String login, String password) {
        super();
        this.firstName = firstName;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

}
