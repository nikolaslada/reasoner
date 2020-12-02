package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.rest.swagger.domains.OntologyDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OntologyApi {

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

}
