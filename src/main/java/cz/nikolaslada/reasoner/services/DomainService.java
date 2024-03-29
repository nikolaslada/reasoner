package cz.nikolaslada.reasoner.services;

import cz.nikolaslada.reasoner.domains.IdNamePairsDomain;
import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.factories.DomainFactory;
import cz.nikolaslada.reasoner.repository.ClassNodeRepository;
import cz.nikolaslada.reasoner.repository.PropertyRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
public class DomainService {

    private final DomainFactory domainFactory;
    private final ClassNodeRepository classNodeRepository;
    private final PropertyRepository propertyRepository;


    public DomainService(
            DomainFactory domainFactory,
            ClassNodeRepository classNodeRepository,
            PropertyRepository propertyRepository
    ) {
        this.domainFactory = domainFactory;
        this.classNodeRepository = classNodeRepository;
        this.propertyRepository = propertyRepository;
    }

    public IdNamePairsDomain getIdNamePairs(ObjectId ontologyId) {
        return this.domainFactory.createIdNamePairs(
                this.classNodeRepository.findAllByOntologyId(ontologyId),
                this.propertyRepository.findAllByOntologyId(ontologyId)
        );
    }

    public NameIdPairsDomain getNameIdPairs(ObjectId ontologyId) {
        return this.domainFactory.createNameIdPairs(
                this.classNodeRepository.findAllByOntologyId(ontologyId),
                this.propertyRepository.findAllByOntologyId(ontologyId)
        );
    }

}
