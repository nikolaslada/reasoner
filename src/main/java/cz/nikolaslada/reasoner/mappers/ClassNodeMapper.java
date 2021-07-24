package cz.nikolaslada.reasoner.mappers;

import cz.nikolaslada.reasoner.repository.model.ClassNodeModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.ClassDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ObjectIdToStringMapper.class, ObjectIdToDateMapper.class})
public interface ClassNodeMapper {

    ClassNodeMapper INSTANCE = Mappers.getMapper(ClassNodeMapper.class);

    ClassDetail classNodeModelToClassDetail(ClassNodeModel classNodeModel);

}
