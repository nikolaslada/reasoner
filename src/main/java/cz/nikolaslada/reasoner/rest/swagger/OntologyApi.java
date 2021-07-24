package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.mappers.OntologyMapper;
import cz.nikolaslada.reasoner.repository.model.OntologyModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.OntologyResponse;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewOntologyRequest;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.GoneException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
import cz.nikolaslada.reasoner.services.OntologyService;
import cz.nikolaslada.reasoner.services.ValidatorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class OntologyApi {

    private final OntologyService service;
    private final ValidatorService validatorService;

    public OntologyApi(OntologyService service, ValidatorService validatorService) {
        this.service = service;
        this.validatorService = validatorService;
    }


    @GetMapping("/ontology/{id}")
    public OntologyResponse getOntology(@PathVariable String id) throws NotFoundException {
        OntologyModel ontology = this.service.getById(id);
        return OntologyMapper.INSTANCE.ontologyModelToOntologyDetail(ontology);
    }

    @PostMapping(
            value = "/ontology",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public OntologyResponse post(@RequestBody NewOntologyRequest request) throws ErrorException {
        this.validatorService.validate(request);
        return this.service.create(request);
    }

    @DeleteMapping("/ontology/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) throws GoneException {
        this.service.delete(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
