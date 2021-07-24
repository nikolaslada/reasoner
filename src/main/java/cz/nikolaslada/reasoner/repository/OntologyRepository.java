package cz.nikolaslada.reasoner.repository;

import cz.nikolaslada.reasoner.repository.model.OntologyModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OntologyRepository extends MongoRepository<OntologyModel, String> {

    OntologyModel findById(ObjectId id);
    void deleteById(ObjectId id);
    Boolean existsById(ObjectId id);
    Boolean existsByName(String name);

}
