package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewUser;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.UserDetail;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ConflictException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.services.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class UserApi {

    private static final String BY_ID = "id";
    private static final String BY_LOGIN = "login";
    private static final String BAD_REQUEST_UNSUPPORTED_BY = "Not supported to get user by: ";

    private final UserService service;


    public UserApi(UserService service) {
        this.service = service;
    }

    @GetMapping("/user/{by}/{value}")
    public UserDetail getUserByIdOrLogin(@PathVariable() String by, @PathVariable() String value) throws ErrorException {
        if (BY_ID.contentEquals(by)) {
            return this.service.getById(
                    Integer.parseInt(value)
            );
        } else if (BY_LOGIN.contentEquals(by)) {
            return this.service.getByLogin(value);
        } else {
            throw new BadRequestException(
                    BAD_REQUEST_UNSUPPORTED_BY,
                    Arrays.asList(
                            new ErrorItem(
                                    BAD_REQUEST_UNSUPPORTED_BY,
                                    Arrays.asList(
                                            by
                                    )
                            )
                    )
            );
        }
    }

    @PostMapping(
            value = "/user",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public UserDetail post(@RequestBody NewUser request) throws ConflictException {
        return this.service.create(request);
    }

}
