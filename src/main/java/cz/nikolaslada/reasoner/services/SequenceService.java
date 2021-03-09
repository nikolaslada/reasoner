package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.repository.SequenceRepository;
import cz.nikolaslada.reasoner.repository.model.Sequence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

@Service
public class SequenceService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SequenceRepository sequenceRepository;


    public Sequence getNewSequence(String sequenceName, Integer increment) {
        Criteria criteria = Criteria.where("uid").is(sequenceName);
        Query query = new Query(criteria);

        Update update = new Update();
        update.inc("seq", increment);

        FindAndModifyOptions options = new FindAndModifyOptions();
        options.upsert(false);
        options.returnNew(true);

        Sequence sequence = this.mongoTemplate.findAndModify(query, update, options, Sequence.class);

        if (sequence == null) {
            sequence = this.createSequence(sequenceName);
        }

        return sequence;
    }

    private Sequence createSequence(String sequenceName) {
        return this.sequenceRepository.save(
                new Sequence(
                        sequenceName,
                        1
                )
        );
    }

}
