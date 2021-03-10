package cz.nikolaslada.reasoner.repository;

import cz.nikolaslada.reasoner.repository.model.Property;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends MongoRepository<Property, String> {

    Boolean existsByOntologyId(Integer id);
    List<Property> findAllByOntologyId(Integer id);

}
