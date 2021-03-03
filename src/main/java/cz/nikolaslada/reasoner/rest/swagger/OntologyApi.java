package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.mappers.OntologyMapper;
import cz.nikolaslada.reasoner.repository.model.Ontology;
import cz.nikolaslada.reasoner.rest.swagger.domains.OntologyDetail;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewOntology;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
import cz.nikolaslada.reasoner.services.OntologyService;
import org.springframework.web.bind.annotation.*;

@RestController
public class OntologyApi {

    private final OntologyService service;

    public OntologyApi(OntologyService service) {
        this.service = service;
    }


    @GetMapping("/ontology/{id}")
    public OntologyDetail getOntology(@PathVariable int id) throws NotFoundException {
        Ontology ontology = this.service.getById(id);
        return OntologyMapper.INSTANCE.ontologyModelToOntologyDetail(ontology);
    }

    @PostMapping(
            value = "/ontology",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public Ontology post(@RequestBody NewOntology request) throws ErrorException {
        return this.service.create(request);
    }

}
