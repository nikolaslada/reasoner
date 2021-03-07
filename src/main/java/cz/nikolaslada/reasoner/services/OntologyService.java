package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.factories.TranslationFactory;
import cz.nikolaslada.reasoner.repository.model.Ontology;
import cz.nikolaslada.reasoner.repository.OntologyRepository;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewOntology;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ConflictException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.GoneException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OntologyService {

    private static final String CONFLICT_MESSAGE_BY_NAME = "There is Ontology with same name: ";
    private static final String GONE_MESSAGE = "Cannot to remove Ontology. There is no Ontology with ID: ";
    private static final String NOT_FOUND_MESSAGE = "There is no Ontology with ID: ";

    private final OntologyRepository ontologyRepository;
    private final SequenceService sequenceService;
    private final TranslationFactory translationFactory;


    public OntologyService(
            OntologyRepository ontologyRepository,
            SequenceService sequenceService,
            TranslationFactory translationFactory
    ) {
        this.ontologyRepository = ontologyRepository;
        this.sequenceService = sequenceService;
        this.translationFactory = translationFactory;
    }

    public Ontology getById(Integer id) throws NotFoundException {
        Ontology ontology = this.ontologyRepository.findById(id);

        if (ontology == null) {
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
            return ontology;
        }
    }

    public Ontology create(NewOntology request) throws ErrorException {
        if (this.ontologyRepository.existsByName(request.getName())) {
            throw new ConflictException(
                    CONFLICT_MESSAGE_BY_NAME,
                    Arrays.asList(
                            new ErrorItem(
                                    CONFLICT_MESSAGE_BY_NAME,
                                    Arrays.asList(
                                            request.getName()
                                    )
                            )
                    )
            );
        }

        return this.ontologyRepository.save(
                new Ontology(
                    this.sequenceService.getNewSequence(Ontology.SEQUENCE_NAME, 1).getSeq(),
                    request.getName(),
                    this.translationFactory.createModelList(request.getTranslationList()),
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
                            new ErrorItem(
                                    GONE_MESSAGE,
                                    Arrays.asList(
                                            id.toString()
                                    )
                            )
                    )
            );
        }

        this.ontologyRepository.deleteById(id);
    }

}
