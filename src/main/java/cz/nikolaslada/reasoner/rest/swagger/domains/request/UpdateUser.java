package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@Getter
public class UpdateUser {

    @Size(min = 1, max = 255, message = "firstName must be between 1 and 255 characters")
    private final String firstName;

    @Size(min = 1, max = 255, message = "surname must be between 1 and 255 characters")
    private final String surname;

    @Size(min = 8, max = 255, message = "password must be between 8 and 255 characters")
    private final String password;

    @Email(message = "email is not valid")
    private final String email;


    public UpdateUser(String firstName, String surname, String password, String email) {
        super();
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
        this.email = email;
    }

}
