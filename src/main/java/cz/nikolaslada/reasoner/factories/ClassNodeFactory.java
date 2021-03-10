package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.domains.IdNamePairsDomain;
import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.repository.model.ClassSetModel;
import cz.nikolaslada.reasoner.repository.model.ConditionModel;
import cz.nikolaslada.reasoner.repository.model.DefinitionModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.ConditionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.DefinitionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.ClassSetDomain;
import cz.nikolaslada.reasoner.validators.ConditionValidator;
import cz.nikolaslada.reasoner.validators.PropertyValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cz.nikolaslada.reasoner.repository.identifiers.ConditionTypeId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.ConditionTypeId.*;

@Component
public class ClassNodeFactory {

    private final ConditionValidator conditionValidator;
    private final PropertyValidator propertyValidator;


    public ClassNodeFactory(ConditionValidator conditionValidator, PropertyValidator propertyValidator) {
        this.conditionValidator = conditionValidator;
        this.propertyValidator = propertyValidator;
    }

    public ConditionModel createConditionModel(ConditionDomain domain, NameIdPairsDomain nameIdPairs) throws Exception {
        List<ConditionModel> set = new ArrayList<>();
        for (ConditionDomain cD : domain.getSet()) {
            set.add(this.createConditionModel(cD, nameIdPairs));
        }

        switch (domain.getType()) {
            case SET_API:
                return new ConditionModel(
                        SET_DB,
                        this.conditionValidator.getDbOperator(domain.getOp()),
                        set,
                        null,
                        null,
                        null,
                        null
                );
            case CLASS_API:
                return new ConditionModel(
                        CLASS_DB,
                        null,
                        null,
                        nameIdPairs.getClassIdNameMap().get(domain.getName()),
                        null,
                        null,
                        null
                );
            case PROPERTY_API:
                return new ConditionModel(
                        PROPERTY_DB,
                        null,
                        set.isEmpty() ? null : set,
                        null,
                        nameIdPairs.getPropertyIdNameMap().get(domain.getName()),
                        this.propertyValidator.getDbRestriction(domain.getRestriction()),
                        domain.getVal()
                );
            case TYPE_NOT_API:
                return new ConditionModel(
                        TYPE_NOT_DB,
                        null,
                        set,
                        null,
                        null,
                        null,
                        null
                );
            default:
                throw new Exception(
                        String.format(
                                "Not supported id of condition type '%c'.",
                                domain.getType()
                        )
                );
        }
    }

    public ConditionDomain createConditionDomain(ConditionModel model, IdNamePairsDomain idNamePairs) throws Exception {
        List<ConditionDomain> set = new ArrayList<>();
        for (ConditionModel cM : model.getSet()) {
            set.add(this.createConditionDomain(cM, idNamePairs));
        }

        switch (model.getType()) {
            case SET_DB:
                return new ConditionDomain(
                        SET_API,
                        this.conditionValidator.getApiOperator(model.getOp()),
                        set,
                        null,
                        null,
                        null
                );
            case CLASS_DB:
                return new ConditionDomain(
                        CLASS_API,
                        null,
                        null,
                        idNamePairs.getClassIdNameMap().get(model.getClassId()),
                        null,
                        null
                );
            case PROPERTY_DB:
                return new ConditionDomain(
                        PROPERTY_API,
                        null,
                        set.isEmpty() ? null : set,
                        idNamePairs.getPropertyIdNameMap().get(model.getPropertyId()),
                        this.propertyValidator.getApiRestriction(model.getRestrict()),
                        model.getVal()
                );
            case TYPE_NOT_DB:
                return new ConditionDomain(
                        TYPE_NOT_API,
                        null,
                        set,
                        null,
                        null,
                        null
                );
            default:
                throw new Exception(
                        String.format(
                                "Not supported id of condition type '%c'.",
                                model.getType()
                        )
                );
        }
    }

    public List<DefinitionModel> createDefinitionModelList(List<DefinitionDomain> list, NameIdPairsDomain nameIdPairs) throws Exception {
        List<DefinitionModel> set = new ArrayList<>();
        for (DefinitionDomain d : list) {
            set.add(this.createDefinitionModel(d, nameIdPairs));
        }

        return set;
    }

    public DefinitionModel createDefinitionModel(DefinitionDomain d, NameIdPairsDomain nameIdPairs) throws Exception {
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

    public List<DefinitionDomain> createDefinitionDomainList(List<DefinitionModel> list, IdNamePairsDomain idNamePairs) throws Exception {
        List<DefinitionDomain> set = new ArrayList<>();
        for (DefinitionModel m : list) {
            set.add(this.createDefinitionDomain(m, idNamePairs));
        }

        return set;
    }

    public DefinitionDomain createDefinitionDomain(DefinitionModel m, IdNamePairsDomain idNamePairs) throws Exception {
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