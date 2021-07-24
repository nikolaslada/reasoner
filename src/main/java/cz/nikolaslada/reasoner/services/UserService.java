package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.mappers.UserMapper;
import cz.nikolaslada.reasoner.repository.UserRepository;
import cz.nikolaslada.reasoner.repository.model.User;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewUser;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.UpdateUser;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.UserDetail;
import cz.nikolaslada.reasoner.rest.swagger.error.BadRequestBuilder;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.*;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;

@Service
public class UserService {

    private static final String CONFLICT_MESSAGE = "There is User with same login: ";
    private static final String NOT_FOUND_MESSAGE_BY_ID = "There is no User with ID: ";
    private static final String NOT_FOUND_MESSAGE_BY_LOGIN = "There is no User with Login: ";
    private static final String GONE_MESSAGE = "Cannot to remove User. There is no User with ID: ";

    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetail getById(String id) throws ErrorException {
        try {
            User user = this.userRepository.findById(new ObjectId(id));

            if (user == null) {
                throw new NotFoundException(
                        NOT_FOUND_MESSAGE_BY_ID,
                        Arrays.asList(
                                id
                        )
                );
            } else {
                return UserMapper.INSTANCE.userModelToUserDetail(user);
            }
        } catch (IllegalArgumentException e) {
            throw new BadRequestBuilder()
                    .addErrorItem(e.getMessage())
                    .build();
        }
    }

    public UserDetail getByLogin(String login) throws NotFoundException {
        User user = this.userRepository.findByLogin(login);

        if (user == null) {
            throw new NotFoundException(
                    NOT_FOUND_MESSAGE_BY_LOGIN,
                    Arrays.asList(
                            login
                    )
            );
        } else {
            return UserMapper.INSTANCE.userModelToUserDetail(user);
        }
    }

    public UserDetail create(NewUser request) throws ConflictException {
        if (this.userRepository.existsByLogin(request.getLogin())) {
            throw new ConflictException(
                    CONFLICT_MESSAGE,
                    Arrays.asList(
                            request.getLogin()
                    )
            );
        }

        User user = this.userRepository.save(
                new User(
                        new ObjectId(),
                        request.getLogin(),
                        "",
                        request.getFirstName(),
                        request.getSurname(),
                        ZonedDateTime.now(ZoneOffset.UTC)
                )
        );

        return UserMapper.INSTANCE.userModelToUserDetail(user);
    }

    public void delete(String id) {
        ObjectId objectId = new ObjectId(id);
        if (!this.userRepository.existsById(objectId)) {
            throw new GoneException(
                    GONE_MESSAGE,
                    Arrays.asList(
                            id
                    )
            );
        } else {
            this.userRepository.deleteById(objectId);
        }
    }

    public UserDetail patch(String id, UpdateUser r) throws GoneException {
        User user = this.userRepository.findById(new ObjectId(id));
        if (user == null) {
            throw new GoneException(
                    GONE_MESSAGE,
                    Arrays.asList(
                            id
                    )
            );
        }

        User patchedUser = this.userRepository.save(
                new User(
                        user.getId(),
                        user.getLogin(),
                        "",
                        (r.getFirstName() == null || r.getFirstName().isEmpty()) ? user.getFirstName() : r.getFirstName(),
                        (r.getSurname() == null || r.getSurname().isEmpty()) ? user.getSurname() : r.getSurname(),
                        ZonedDateTime.now(ZoneOffset.UTC)
                )
        );

        return UserMapper.INSTANCE.userModelToUserDetail(patchedUser);
    }

}
