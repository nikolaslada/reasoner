package cz.nikolaslada.reasoner.mappers;

import cz.nikolaslada.reasoner.repository.model.Ontology;
import cz.nikolaslada.reasoner.rest.swagger.domains.OntologyDetail;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OntologyMapper {

    OntologyMapper INSTANCE = Mappers.getMapper(OntologyMapper.class);

    OntologyDetail OntologyModelToOntologyDetail(Ontology ontology);

}
