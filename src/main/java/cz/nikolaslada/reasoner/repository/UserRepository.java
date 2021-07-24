package cz.nikolaslada.reasoner.repository;

import cz.nikolaslada.reasoner.repository.model.UserModel;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {

    Boolean existsByLogin(String login);
    Boolean existsById(ObjectId id);
    void deleteById(ObjectId id);
    UserModel findById(ObjectId id);
    UserModel findByLogin(String login);

}
