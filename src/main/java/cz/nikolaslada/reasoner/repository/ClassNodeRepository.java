package cz.nikolaslada.reasoner.repository;

import cz.nikolaslada.reasoner.repository.model.ClassNode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassNodeRepository extends MongoRepository<ClassNode, String> {

    Boolean existsByName(String name);
    ClassNode findById(Integer id);

}
