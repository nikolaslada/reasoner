package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.factories.SharedFactory;
import cz.nikolaslada.reasoner.repository.ClassNodeRepository;
import cz.nikolaslada.reasoner.repository.PropertyRepository;
import cz.nikolaslada.reasoner.repository.model.Ontology;
import cz.nikolaslada.reasoner.repository.OntologyRepository;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewOntologyDomain;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ConflictException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.GoneException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

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

    public Ontology getById(Integer id) throws NotFoundException {
        Ontology ontology = this.ontologyRepository.findById(id);

        if (ontology == null) {
            throw new NotFoundException(
                    NOT_FOUND_MESSAGE,
                    Arrays.asList(
                            id.toString()
                    )
            );
        } else {
            return ontology;
        }
    }

    public Ontology create(NewOntologyDomain request) throws ErrorException {
        if (this.ontologyRepository.existsByName(request.getName())) {
            throw new ConflictException(
                    CONFLICT_MESSAGE_BY_NAME,
                    Arrays.asList(
                            request.getName()
                    )
            );
        }

        return this.ontologyRepository.save(
                new Ontology(
                    this.sequenceService.getNewSequence(Ontology.SEQUENCE_NAME, 1).getSeq(),
                    request.getName(),
                    this.sharedFactory.createTranslationModelList(request.getTranslationList()),
                    // owner
                    null,
                    null,
                    null
                )
        );
    }

    public void delete(Integer id) throws ErrorException {
        if (!this.ontologyRepository.existsById(id)) {
            throw new GoneException(
                    GONE_MESSAGE,
                    Arrays.asList(
                            id.toString()
                    )
            );
        }

        if (
                this.classNodeRepository.existsByOntologyId(id)
                || this.propertyRepository.existsByOntologyId(id)
        ) {
            throw new ConflictException(
                    CONFLICT_MESSAGE_BY_REF,
                    Arrays.asList(
                            id.toString()
                    )
            );
        }

        this.ontologyRepository.deleteById(id);
    }

}
