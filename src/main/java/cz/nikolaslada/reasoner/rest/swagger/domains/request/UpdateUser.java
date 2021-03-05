package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import lombok.Getter;

@Getter
public class UpdateUser {

    private final String firstName;
    private final String surname;
    private final String password;


    public UpdateUser(String firstName, String surname, String password) {
        super();
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
    }

}
