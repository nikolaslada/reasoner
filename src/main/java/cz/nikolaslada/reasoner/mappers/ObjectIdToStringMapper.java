package cz.nikolaslada.reasoner.mappers;

import org.bson.types.ObjectId;

public class ObjectIdToStringMapper {

    String map(ObjectId value) {
        return value.toString();
    }

}
