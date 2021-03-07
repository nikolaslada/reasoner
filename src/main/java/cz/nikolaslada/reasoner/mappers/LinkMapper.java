package cz.nikolaslada.reasoner.mappers;

import cz.nikolaslada.reasoner.repository.model.LinkModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.LinkDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LinkMapper {

    LinkMapper INSTANCE = Mappers.getMapper(LinkMapper.class);

    LinkDomain modelToDomain (LinkModel model);

    LinkModel domainToModel (LinkDomain domain);

}
