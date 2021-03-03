package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.mappers.UserMapper;
import cz.nikolaslada.reasoner.repository.UserRepository;
import cz.nikolaslada.reasoner.repository.model.User;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewUser;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.UserDetail;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ConflictException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;

@Service
public class UserService {

    private static final String CONFLICT_MESSAGE = "There is User with same login: ";
    private static final String NOT_FOUND_MESSAGE_BY_ID = "There is no User with ID: ";
    private static final String NOT_FOUND_MESSAGE_BY_LOGIN = "There is no User with Login: ";

    private final UserRepository userRepository;
    private final SequenceService sequenceService;


    public UserService(UserRepository userRepository, SequenceService sequenceService) {
        this.userRepository = userRepository;
        this.sequenceService = sequenceService;
    }

    public UserDetail getById(Integer id) throws NotFoundException {
        User user = this.userRepository.findById(id);

        if (user == null) {
            throw new NotFoundException(
                    NOT_FOUND_MESSAGE_BY_ID,
                    Arrays.asList(
                            new ErrorItem(
                                    NOT_FOUND_MESSAGE_BY_ID,
                                    Arrays.asList(
                                            id.toString()
                                    )
                            )
                    )
            );
        } else {
            return UserMapper.INSTANCE.userModelToUserDetail(user);
        }
    }

    public UserDetail getByLogin(String login) throws NotFoundException {
        User user = this.userRepository.findByLogin(login);

        if (user == null) {
            throw new NotFoundException(
                    NOT_FOUND_MESSAGE_BY_LOGIN,
                    Arrays.asList(
                            new ErrorItem(
                                    NOT_FOUND_MESSAGE_BY_LOGIN,
                                    Arrays.asList(
                                            login
                                    )
                            )
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
                            new ErrorItem(
                                    CONFLICT_MESSAGE,
                                    Arrays.asList(
                                            request.getLogin()
                                    )
                            )
                    )
            );
        }

        User user = this.userRepository.save(
                new User(
                        this.sequenceService.getNewSequence(User.SEQUENCE_NAME, 1).getSeq(),
                        request.getLogin(),
                        "",
                        request.getFirstName(),
                        request.getSurname(),
                        new Date(),
                        null
                )
        );

        return UserMapper.INSTANCE.userModelToUserDetail(user);
    }

}
