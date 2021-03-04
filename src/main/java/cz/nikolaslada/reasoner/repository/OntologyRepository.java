package cz.nikolaslada.reasoner.repository;

import cz.nikolaslada.reasoner.repository.model.Ontology;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OntologyRepository extends MongoRepository<Ontology, String> {

    Ontology findById(Integer id);
    void deleteById(Integer id);
    Boolean existsById(Integer id);
    Boolean existsByName(String name);

}
