package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.rest.swagger.domains.MyOntologies;
import cz.nikolaslada.reasoner.rest.swagger.domains.MyOntologyItem;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class MyOntologiesApi {

    @GetMapping("/my-ontologies")
    public MyOntologies getMyOntologies() {
        List<MyOntologyItem> ontologyList = Arrays.asList(
                new MyOntologyItem(1, "Test ontology #1", 256, 32, 0),
                new MyOntologyItem(
                        12,
                        "Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789Abcdefghijklmnopqrstuvwxyz0123456789abc",
                        2147483647,
                        32768,
                        16777216),
                new MyOntologyItem(13, null, 0, 0, 0)
        );
        return new MyOntologies(
                10,
                3,
                13,
                ontologyList
        );
    }

}
