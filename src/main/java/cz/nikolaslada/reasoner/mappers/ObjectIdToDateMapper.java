package cz.nikolaslada.reasoner.mappers;

import org.bson.types.ObjectId;

import java.util.Date;

public class ObjectIdToDateMapper {

    Date map(ObjectId value) {
        return value.getDate();
    }

}
