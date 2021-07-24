package cz.nikolaslada.reasoner.repository;

import cz.nikolaslada.reasoner.repository.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Boolean existsByLogin(String login);
    User findById(ObjectId id);
    User findByLogin(String login);

}
