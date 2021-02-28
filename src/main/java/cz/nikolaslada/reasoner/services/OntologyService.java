package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.repository.model.Ontology;
import cz.nikolaslada.reasoner.repository.OntologyRepository;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewOntology;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ConflictException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
import cz.nikolaslada.reasoner.validators.IsoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OntologyService {

    private static final String CONFLICT_MESSAGE = "There is Ontology with same name: ";
    private static final String NOT_FOUND_MESSAGE = "There is no Ontology with ID: ";

    @Autowired
    private OntologyRepository ontologyRepository;

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private IsoValidator isoValidator;


    public Ontology getById(Integer id) throws NotFoundException {
        Ontology ontology = this.ontologyRepository.findOntologyById(id);

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

    public Ontology save(NewOntology request) throws ErrorException {
        if (this.ontologyRepository.existsByName(request.getName())) {
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
        return this.ontologyRepository.save(
                new Ontology(
                    this.sequenceService.getNewSequence(Ontology.SEQUENCE_NAME, 1).getSeq(),
                    request.getName(),
                    request.getTranslationList(),
                    // owner
                    null,
                    null,
                    null
                )
        );
    }

    public Boolean deleteOntology(String id) {
        return this.ontologyRepository.deleteOntologyById(id);
    }

}
