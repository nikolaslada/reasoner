package cz.nikolaslada.reasoner.repository;

import cz.nikolaslada.reasoner.repository.model.Sequence;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SequenceRepository extends MongoRepository<Sequence, String> {
}
