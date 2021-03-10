package cz.nikolaslada.reasoner.repository;

import cz.nikolaslada.reasoner.repository.model.ClassNodeModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassNodeRepository extends MongoRepository<ClassNodeModel, String> {

    Boolean existsByName(String name);
    Boolean existsByOntologyId(Integer id);
    ClassNodeModel findById(Integer id);
    List<ClassNodeModel> findAllByOntologyId(Integer id);

}
