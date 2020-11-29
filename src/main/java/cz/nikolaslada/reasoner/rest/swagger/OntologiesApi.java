package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.rest.swagger.domains.Ontologies;
import cz.nikolaslada.reasoner.rest.swagger.domains.OntologyItem;
import cz.nikolaslada.reasoner.rest.swagger.domains.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class OntologiesApi {

    @GetMapping("/ontologies")
    public Ontologies getOntologies() {
        User owner = new User(1, "Admin");
        List<OntologyItem> ontologyList = Arrays.asList(
                new OntologyItem(1, "Test ontology #1", 256, 32, 0, owner),
                new OntologyItem(
                        12,
                        "Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789abc",
                        2147483647,
                        32768,
                        16777216,
                        owner),
                new OntologyItem(13, null, 0, 0, 0, owner)
        );
        return new Ontologies(
                10,
                3,
                13,
                ontologyList
        );
    }

}
