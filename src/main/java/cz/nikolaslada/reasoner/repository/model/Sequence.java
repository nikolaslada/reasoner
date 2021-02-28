package cz.nikolaslada.reasoner.repository.model;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@Document(collection = "sequences")
public class Sequence {

    @MongoId
    private String uid;

    private int seq;


    public Sequence(String uid, int seq) {
        this.uid = uid;
        this.seq = seq;
    }

}
