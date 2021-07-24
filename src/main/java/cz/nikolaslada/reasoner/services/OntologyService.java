package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.factories.SharedFactory;
import cz.nikolaslada.reasoner.mappers.OntologyMapper;
import cz.nikolaslada.reasoner.repository.ClassNodeRepository;
import cz.nikolaslada.reasoner.repository.PropertyRepository;
import cz.nikolaslada.reasoner.repository.model.OntologyModel;
import cz.nikolaslada.reasoner.repository.OntologyRepository;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewOntologyRequest;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.OntologyResponse;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ConflictException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.GoneException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;

@Service
public class OntologyService {

    private static final String CONFLICT_MESSAGE_BY_NAME = "There is Ontology with same name: ";
    private static final String CONFLICT_MESSAGE_BY_REF = "There is at least one reference to this Ontology ID: ";
    private static final String GONE_MESSAGE = "Cannot to remove Ontology. There is no Ontology with ID: ";
    private static final String NOT_FOUND_MESSAGE = "There is no Ontology with ID: ";

    private final OntologyRepository ontologyRepository;
    private final ClassNodeRepository classNodeRepository;
    private final PropertyRepository propertyRepository;
    private final SequenceService sequenceService;
    private final SharedFactory sharedFactory;


    public OntologyService(
            OntologyRepository ontologyRepository,
            ClassNodeRepository classNodeRepository,
            PropertyRepository propertyRepository,
            SequenceService sequenceService,
            SharedFactory sharedFactory
    ) {
        this.ontologyRepository = ontologyRepository;
        this.classNodeRepository = classNodeRepository;
        this.propertyRepository = propertyRepository;
        this.sequenceService = sequenceService;
        this.sharedFactory = sharedFactory;
    }

    public OntologyModel getById(String id) throws NotFoundException {
        OntologyModel ontologyModel = this.ontologyRepository.findById(new ObjectId(id));

        if (ontologyModel == null) {
            throw new NotFoundException(
                    NOT_FOUND_MESSAGE,
                    Arrays.asList(
                            id
                    )
            );
        } else {
            return ontologyModel;
        }
    }

    public OntologyResponse create(NewOntologyRequest request) throws ErrorException {
        if (this.ontologyRepository.existsByName(request.getName())) {
            throw new ConflictException(
                    CONFLICT_MESSAGE_BY_NAME,
                    Arrays.asList(
                            request.getName()
                    )
            );
        }

        OntologyModel ontologyModel = this.ontologyRepository.save(
                new OntologyModel(
                    new ObjectId(),
                    request.getName(),
                    this.sharedFactory.createTranslationModelList(request.getTranslationList()),
                    // owner
                    null,
                    null,
                    null,
                    ZonedDateTime.now(ZoneOffset.UTC)
                )
        );

        return OntologyMapper.INSTANCE.ontologyModelToOntologyDetail(ontologyModel);
    }

    public void delete(String id) throws ErrorException {
        ObjectId objectId = new ObjectId(id);
        if (!this.ontologyRepository.existsById(objectId)) {
            throw new GoneException(
                    GONE_MESSAGE,
                    Arrays.asList(
                            id
                    )
            );
        }

        if (
                this.classNodeRepository.existsByOntologyId(objectId)
                || this.propertyRepository.existsByOntologyId(objectId)
        ) {
            throw new ConflictException(
                    CONFLICT_MESSAGE_BY_REF,
                    Arrays.asList(
                            id
                    )
            );
        }

        this.ontologyRepository.deleteById(objectId);
    }

}
