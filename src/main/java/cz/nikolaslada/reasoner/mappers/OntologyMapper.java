package cz.nikolaslada.reasoner.mappers;

import cz.nikolaslada.reasoner.repository.model.OntologyModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.OntologyResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {ObjectIdToStringMapper.class, ObjectIdToDateMapper.class})
public interface OntologyMapper {

    OntologyMapper INSTANCE = Mappers.getMapper(OntologyMapper.class);

    @Mapping(source = "ontologyModel.id", target = "createdAt")
    OntologyResponse ontologyModelToOntologyDetail(OntologyModel ontologyModel);

}
