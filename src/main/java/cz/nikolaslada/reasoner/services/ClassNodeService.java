package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.factories.ClassNodeFactory;
import cz.nikolaslada.reasoner.factories.SharedFactory;
import cz.nikolaslada.reasoner.repository.ClassNodeRepository;
import cz.nikolaslada.reasoner.repository.model.ClassNodeModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewClassDomain;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ConflictException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;

@Service
public class ClassNodeService {

    private static final String CONFLICT_MESSAGE = "There is Class node with same name: ";
    private static final String NOT_FOUND_MESSAGE = "There is no Class node with ID: ";

    private final ClassNodeRepository classNodeRepository;
    private final SequenceService sequenceService;
    private final DomainService domainService;
    private final SharedFactory sharedFactory;
    private final ClassNodeFactory classNodeFactory;


    public ClassNodeService(
            ClassNodeRepository classNodeRepository,
            SequenceService sequenceService,
            DomainService domainService,
            SharedFactory sharedFactory,
            ClassNodeFactory classNodeFactory
    ) {
        this.classNodeRepository = classNodeRepository;
        this.sequenceService = sequenceService;
        this.domainService = domainService;
        this.sharedFactory = sharedFactory;
        this.classNodeFactory = classNodeFactory;
    }

    public ClassNodeModel getById(Integer id) throws NotFoundException {
        ClassNodeModel classNodeModel = this.classNodeRepository.findById(id);

        if (classNodeModel == null) {
            throw new NotFoundException(
                    NOT_FOUND_MESSAGE,
                    Arrays.asList(
                            id.toString()
                    )
            );
        } else {
            return classNodeModel;
        }
    }

    public ClassNodeModel create(NewClassDomain request) throws ConflictException {
        if (this.classNodeRepository.existsByName(request.getName())) {
            throw new ConflictException(
                    CONFLICT_MESSAGE,
                    Arrays.asList(
                            request.getName()
                    )
            );
        }

        NameIdPairsDomain nameIdPairs = this.domainService.getNameIdPairs(request.getOntologyId());
        ClassNodeModel classNodeModel = this.classNodeFactory.createClassNodeModel(
                this.sequenceService.getNewSequence(ClassNodeModel.SEQUENCE_NAME, 1).getSeq(),
                request.getOntologyId(),
                request.getName(),
                // owner
                ZonedDateTime.now(ZoneOffset.UTC),
                null,
                this.sharedFactory.createTranslationModelList(request.getTranslationList()),
                this.sharedFactory.createLinkModel(request.getLinkDomain()),
                request.getDefinitionList(),
                request.getCondition(),
                nameIdPairs
        );
        return this.classNodeRepository.save(classNodeModel);
    }

}
