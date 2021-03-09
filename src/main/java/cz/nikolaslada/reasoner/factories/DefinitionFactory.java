package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.domains.IdNamePairsDomain;
import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.repository.model.ClassSetModel;
import cz.nikolaslada.reasoner.repository.model.DefinitionModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.ClassSetDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.DefinitionDomain;
import cz.nikolaslada.reasoner.validators.ConditionValidator;
import cz.nikolaslada.reasoner.validators.PropertyValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefinitionFactory {

    private final ConditionValidator conditionValidator;
    private final PropertyValidator propertyValidator;


    public DefinitionFactory(ConditionValidator conditionValidator, PropertyValidator propertyValidator) {
        this.conditionValidator = conditionValidator;
        this.propertyValidator = propertyValidator;
    }

    public List<DefinitionModel> createModelList(List<DefinitionDomain> list, NameIdPairsDomain nameIdPairs) throws Exception {
        List<DefinitionModel> set = new ArrayList<>();
        for (DefinitionDomain d : list) {
            set.add(this.createModel(d, nameIdPairs));
        }

        return set;
    }

    public DefinitionModel createModel(DefinitionDomain d, NameIdPairsDomain nameIdPairs) throws Exception {
        return new DefinitionModel(
                d.getClassName() == null ? null : nameIdPairs.getClassIdNameMap().get(d.getClassName()),
                d.getProperty() == null ? null : nameIdPairs.getPropertyIdNameMap().get(d.getProperty()),
                this.propertyValidator.getDbRestriction(d.getRestriction()),
                d.getSet() == null ? null : this.createClassSetModel(d.getSet(), nameIdPairs),
                d.getVal()
        );
    }

    public ClassSetModel createClassSetModel(ClassSetDomain d, NameIdPairsDomain nameIdPairs) throws Exception {
        List<ClassSetModel> set = new ArrayList<>();
        for (ClassSetDomain classSet : d.getSet()) {
            set.add(this.createClassSetModel(classSet, nameIdPairs));
        }

        return new ClassSetModel(
                this.conditionValidator.getDbOperator(d.getOp()),
                set.isEmpty() ? null : set,
                d.getName() == null ? null : nameIdPairs.getClassIdNameMap().get(d.getName())
        );
    }

    public List<DefinitionDomain> createDomainList(List<DefinitionModel> list, IdNamePairsDomain idNamePairs) throws Exception {
        List<DefinitionDomain> set = new ArrayList<>();
        for (DefinitionModel m : list) {
            set.add(this.createDomain(m, idNamePairs));
        }

        return set;
    }

    public DefinitionDomain createDomain(DefinitionModel m, IdNamePairsDomain idNamePairs) throws Exception {
        return new DefinitionDomain(
                idNamePairs.getClassIdNameMap().get(m.getCId()),
                idNamePairs.getPropertyIdNameMap().get(m.getPId()),
                this.propertyValidator.getApiRestriction(m.getRestrict()),
                m.getSet() == null ? null : this.createClassSetDomain(m.getSet(), idNamePairs),
                m.getVal()
        );
    }

    public ClassSetDomain createClassSetDomain(ClassSetModel m, IdNamePairsDomain idNamePairs) throws Exception {
        List<ClassSetDomain> set = new ArrayList<>();
        for (ClassSetModel classSet : m.getSet()) {
            set.add(this.createClassSetDomain(classSet, idNamePairs));
        }

        return new ClassSetDomain(
                this.conditionValidator.getApiOperator(m.getOp()),
                set.isEmpty() ? null : set,
                m.getCId() == null ? null : idNamePairs.getClassIdNameMap().get(m.getCId())
        );
    }

}
