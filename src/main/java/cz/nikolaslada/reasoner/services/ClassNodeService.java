package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.repository.ClassNodeRepository;
import cz.nikolaslada.reasoner.repository.model.ClassNode;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewClassNode;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ConflictException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
import cz.nikolaslada.reasoner.validators.IsoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;

@Service
public class ClassNodeService {

    private static final String CONFLICT_MESSAGE = "There is Class node with same name: ";
    private static final String NOT_FOUND_MESSAGE = "There is no Class node with ID: ";

    @Autowired
    private ClassNodeRepository classNodeRepository;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private IsoValidator isoValidator;


    public ClassNode getById(Integer id) throws NotFoundException {
        ClassNode classNode = this.classNodeRepository.findById(id);

        if (classNode == null) {
            throw new NotFoundException(
                    NOT_FOUND_MESSAGE,
                    Arrays.asList(
                            new ErrorItem(
                                    NOT_FOUND_MESSAGE,
                                    Arrays.asList(
                                            id.toString()
                                    )
                            )
                    )
            );
        } else {
            return classNode;
        }
    }

    public ClassNode create(NewClassNode request) throws ErrorException {
        if (this.classNodeRepository.existsByName(request.getName())) {
            throw new ConflictException(
                    CONFLICT_MESSAGE,
                    Arrays.asList(
                            new ErrorItem(
                                    CONFLICT_MESSAGE,
                                    Arrays.asList(
                                            request.getName()
                                    )
                            )
                    )
            );
        }

        this.isoValidator.checkIsoList(request.getTranslationList());
        return this.classNodeRepository.save(
                new ClassNode(
                    this.sequenceService.getNewSequence(ClassNode.SEQUENCE_NAME, 1).getSeq(),
                    request.getName(),
                    // owner
                    ZonedDateTime.now(ZoneOffset.UTC),
                    null
                )
        );
    }

}
