package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewUser;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.UpdateUser;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.UserDetail;
import cz.nikolaslada.reasoner.rest.swagger.error.BadRequestBuilder;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.GoneException;
import cz.nikolaslada.reasoner.services.UserService;
import cz.nikolaslada.reasoner.services.ValidatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserApi {

    private static final String BY_ID = "id";
    private static final String BY_LOGIN = "login";
    private static final String BAD_REQUEST_UNSUPPORTED_BY_MESSAGE = "Not supported to get user by: ";

    private final UserService service;
    private final ValidatorService validatorService;


    public UserApi(UserService service, ValidatorService validatorService) {
        this.service = service;
        this.validatorService = validatorService;
    }

    @GetMapping("/user/{by}/{value}")
    public UserDetail getUserByIdOrLogin(@PathVariable() String by, @PathVariable() String value) throws ErrorException {
        if (BY_ID.contentEquals(by)) {
            return this.service.getById(value);
        } else if (BY_LOGIN.contentEquals(by)) {
            return this.service.getByLogin(value);
        } else {
            throw new BadRequestBuilder()
                    .addErrorItem(BAD_REQUEST_UNSUPPORTED_BY_MESSAGE, by)
                    .build();
        }
    }

    @PostMapping(
            value = "/user",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public UserDetail post(@RequestBody NewUser request) throws ErrorException {
        this.validatorService.validate(request);
        return this.service.create(request);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws GoneException {
        this.service.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(
            value = "/user/{id}",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public UserDetail patch(@PathVariable String id, @RequestBody UpdateUser request) throws ErrorException {
        return this.service.patch(id, request);
    }

}
