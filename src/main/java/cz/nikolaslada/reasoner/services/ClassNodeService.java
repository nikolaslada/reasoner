package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.factories.ConditionFactory;
import cz.nikolaslada.reasoner.factories.DefinitionFactory;
import cz.nikolaslada.reasoner.factories.LinkFactory;
import cz.nikolaslada.reasoner.factories.TranslationFactory;
import cz.nikolaslada.reasoner.repository.ClassNodeRepository;
import cz.nikolaslada.reasoner.repository.model.ClassNodeModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewClassDomain;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
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
    private final TranslationFactory translationFactory;
    private final LinkFactory linkFactory;
    private final DefinitionFactory definitionFactory;
    private final ConditionFactory conditionFactory;


    public ClassNodeService(
            ClassNodeRepository classNodeRepository,
            SequenceService sequenceService,
            DomainService domainService,
            TranslationFactory translationFactory,
            LinkFactory linkFactory,
            DefinitionFactory definitionFactory,
            ConditionFactory conditionFactory
    ) {
        this.classNodeRepository = classNodeRepository;
        this.sequenceService = sequenceService;
        this.domainService = domainService;
        this.translationFactory = translationFactory;
        this.linkFactory = linkFactory;
        this.definitionFactory = definitionFactory;
        this.conditionFactory = conditionFactory;
    }

    public ClassNodeModel getById(Integer id) throws NotFoundException {
        ClassNodeModel classNodeModel = this.classNodeRepository.findById(id);

        if (classNodeModel == null) {
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
            return classNodeModel;
        }
    }

    public ClassNodeModel create(NewClassDomain request) throws Exception {
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

        NameIdPairsDomain nameIdPairs = this.domainService.getNameIdPairs(request.getOntologyId());
        return this.classNodeRepository.save(
                new ClassNodeModel(
                    this.sequenceService.getNewSequence(ClassNodeModel.SEQUENCE_NAME, 1).getSeq(),
                    request.getOntologyId(),
                    request.getName(),
                    // owner
                    ZonedDateTime.now(ZoneOffset.UTC),
                    null,
                    this.translationFactory.createModelList(request.getTranslationList()),
                    this.linkFactory.createModel(request.getLinkDomain()),
                    this.definitionFactory.createModelList(
                            request.getDefinitionList(),
                            nameIdPairs
                    ),
                    this.conditionFactory.createModel(
                            request.getCondition(),
                            nameIdPairs
                    )
                )
        );
    }

}
