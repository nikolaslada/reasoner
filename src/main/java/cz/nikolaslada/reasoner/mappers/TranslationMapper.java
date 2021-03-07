package cz.nikolaslada.reasoner.mappers;

import cz.nikolaslada.reasoner.repository.model.TranslationModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.TranslationDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TranslationMapper {

    TranslationMapper INSTANCE = Mappers.getMapper(TranslationMapper.class);

    TranslationDomain modelToDomain (TranslationModel model);

    TranslationModel domainToModel (TranslationDomain domain);

}
