package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.repository.model.Ontology;
import cz.nikolaslada.reasoner.rest.swagger.domains.OntologyDetail;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewOntology;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.services.OntologyService;
import org.springframework.web.bind.annotation.*;

@RestController
public class OntologyApi {

    private final OntologyService service;

    public OntologyApi(OntologyService service) {
        this.service = service;
    }

    @GetMapping("/ontology/{id}")
    public OntologyDetail getOntology(@PathVariable int id) {
        return new OntologyDetail(
                1,
                "Test ontology #1",
                256,
                32,
                0
        );
    }

    @PostMapping(
            value = "/ontology",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public Ontology post(@RequestBody NewOntology request) throws ErrorException {
        return this.service.save(request);
    }

}
