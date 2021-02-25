package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.repository.model.Ontology;
import cz.nikolaslada.reasoner.repository.OntologyRepository;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewOntology;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ConflictException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.validators.IsoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class OntologyService {

    private static final String CONFLICT_MESSAGE = "There is Ontology with same name: ";

    @Autowired
    private OntologyRepository ontologyRepository;

    @Autowired
    private IsoValidator isoValidator;

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
