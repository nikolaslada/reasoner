package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class UpdateUser {

    @NotBlank(message = "firstName cannot be blank")
    @Size(min = 1, max = 255, message = "firstName must be between 1 and 255 characters")
    private final String firstName;

    @NotBlank(message = "surname cannot be blank")
    @Size(min = 1, max = 255, message = "surname must be between 1 and 255 characters")
    private final String surname;

    @NotBlank(message = "password cannot be blank")
    @Size(min = 8, max = 255, message = "password must be between 1 and 255 characters")
    private final String password;


    public UpdateUser(String firstName, String surname, String password) {
        super();
        this.firstName = firstName;
        this.surname = surname;
        this.password = password;
    }

}
