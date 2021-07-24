package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class NewUser {

    @NotBlank(message = "firstName cannot be blank")
    @Size(min = 1, max = 255, message = "firstName must be between 1 and 255 characters")
    private final String firstName;

    @NotBlank(message = "surname cannot be blank")
    @Size(min = 1, max = 255, message = "surname must be between 1 and 255 characters")
    private final String surname;

    @NotBlank(message = "login cannot be blank")
    @Size(min = 1, max = 255, message = "login must be between 1 and 255 characters")
    private final String login;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 8, max = 255, message = "password must be between 8 and 255 characters")
    private final String password;

    @NotBlank(message = "email cannot be blank")
    @Email(message = "email is not valid")
    private final String email;

    public NewUser(String firstName, String surname, String login, String password, String email) {
        super();
        this.firstName = firstName;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.email = email;
    }

}
