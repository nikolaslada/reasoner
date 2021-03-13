package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.domains.IdNamePairsDomain;
import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.repository.model.ClassSetModel;
import cz.nikolaslada.reasoner.repository.model.ConditionModel;
import cz.nikolaslada.reasoner.repository.model.DefinitionModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.ConditionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.DefinitionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.ClassSetDomain;
import cz.nikolaslada.reasoner.rest.swagger.error.BadRequestBuilder;
import cz.nikolaslada.reasoner.rest.swagger.error.ErrorItem;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.InternalException;
import cz.nikolaslada.reasoner.validators.ClassValidator;
import cz.nikolaslada.reasoner.validators.PropertyValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cz.nikolaslada.reasoner.repository.identifiers.ConditionTypeId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.ConditionTypeId.*;

@Component
public class ClassNodeFactory {

    public static final String DEFINITION_SET_OP_CLASS = "Set in Definition must not contain null or non-null values for both 'op' and 'class' attributes!";
    public static final String DEFINITION_SET_OP_SET = "Set in Definition must contain null or non-null values for both 'op' and 'set' attributes!";

    private final ClassValidator classValidator;
    private final PropertyValidator propertyValidator;


    public ClassNodeFactory(ClassValidator classValidator, PropertyValidator propertyValidator) {
        this.classValidator = classValidator;
        this.propertyValidator = propertyValidator;
    }

    public ConditionModel createConditionModel(ConditionDomain domain, NameIdPairsDomain nameIdPairs) throws BadRequestException {
        List<ConditionModel> set = new ArrayList<>();
        for (ConditionDomain cD : domain.getSet()) {
            set.add(this.createConditionModel(cD, nameIdPairs));
        }

        switch (domain.getType()) {
            case SET_API:
                return new ConditionModel(
                        SET_DB,
                        this.classValidator.getDbOperator(domain.getOp()),
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
                throw new BadRequestException(
                        Arrays.asList(
                                new ErrorItem(
                                        "Not supported id of condition type '%c'.",
                                        Arrays.asList(
                                                domain.getType()
                                        )
                                )
                        )
                );
        }
    }

    public ConditionDomain createConditionDomain(ConditionModel model, IdNamePairsDomain idNamePairs) throws InternalException {
        List<ConditionDomain> set = new ArrayList<>();
        for (ConditionModel cM : model.getSet()) {
            set.add(this.createConditionDomain(cM, idNamePairs));
        }

        switch (model.getType()) {
            case SET_DB:
                return new ConditionDomain(
                        SET_API,
                        this.classValidator.getApiOperator(model.getOp()),
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
                throw new InternalException(
                        "Not supported id of condition type '%c'.",
                        Arrays.asList(
                                model.getType()
                        )
                );
        }
    }

    public List<DefinitionModel> createDefinitionModelList(List<DefinitionDomain> list, NameIdPairsDomain nameIdPairs) throws BadRequestException {
        List<DefinitionModel> set = new ArrayList<>();
        for (DefinitionDomain d : list) {
            set.add(this.createDefinitionModel(d, nameIdPairs));
        }

        return set;
    }

    public DefinitionModel createDefinitionModel(DefinitionDomain d, NameIdPairsDomain nameIdPairs) throws BadRequestException {
        return new DefinitionModel(
                d.getClassName() == null ? null : nameIdPairs.getClassIdNameMap().get(d.getClassName()),
                d.getProperty() == null ? null : nameIdPairs.getPropertyIdNameMap().get(d.getProperty()),
                this.propertyValidator.getDbRestriction(d.getRestriction()),
                d.getSet() == null ? null : this.createClassSetModel(d.getSet(), nameIdPairs, new BadRequestBuilder(), 0),
                d.getVal()
        );
    }

    public ClassSetModel createClassSetModel(
            ClassSetDomain d,
            NameIdPairsDomain nameIdPairs,
            BadRequestBuilder badRequestBuilder,
            int level
    ) throws BadRequestException {
        String dbOp;
        Integer cId;
        List<ClassSetModel> set;

        if (d.getOp() == null) {
            dbOp = null;
            cId = nameIdPairs.getClassIdNameMap().get(d.getName());
            set = null;

            if (d.getName() == null) {
                badRequestBuilder.addErrorItem(DEFINITION_SET_OP_CLASS);
            }

            if (d.getSet() != null) {
                badRequestBuilder.addErrorItem(DEFINITION_SET_OP_SET);
            }
        } else {
            dbOp = this.classValidator.getDbOperator(d.getOp());
            cId = null;
            set = new ArrayList<>();

            if (d.getName() != null) {
                badRequestBuilder.addErrorItem(DEFINITION_SET_OP_CLASS, Arrays.asList(d.getOp(), d.getName()));
            }

            if (d.getSet() == null || d.getSet().isEmpty()) {
                badRequestBuilder.addErrorItem(DEFINITION_SET_OP_SET);
            } else {
                for (ClassSetDomain classSet : d.getSet()) {
                    set.add(this.createClassSetModel(classSet, nameIdPairs, badRequestBuilder, level + 1));
                }
            }
        }

        if (level == 0 && !badRequestBuilder.isEmpty()) {
            throw badRequestBuilder.build();
        }

        return new ClassSetModel(
                dbOp,
                set,
                cId
        );
    }

    public List<DefinitionDomain> createDefinitionDomainList(List<DefinitionModel> list, IdNamePairsDomain idNamePairs) throws InternalException {
        List<DefinitionDomain> set = new ArrayList<>();
        for (DefinitionModel m : list) {
            set.add(this.createDefinitionDomain(m, idNamePairs));
        }

        return set;
    }

    public DefinitionDomain createDefinitionDomain(DefinitionModel m, IdNamePairsDomain idNamePairs) throws InternalException {
        return new DefinitionDomain(
                idNamePairs.getClassIdNameMap().get(m.getCId()),
                idNamePairs.getPropertyIdNameMap().get(m.getPId()),
                this.propertyValidator.getApiRestriction(m.getRestrict()),
                m.getSet() == null ? null : this.createClassSetDomain(m.getSet(), idNamePairs),
                m.getVal()
        );
    }

    public ClassSetDomain createClassSetDomain(ClassSetModel m, IdNamePairsDomain idNamePairs) throws InternalException {
        List<ClassSetDomain> set = new ArrayList<>();
        for (ClassSetModel classSet : m.getSet()) {
            set.add(this.createClassSetDomain(classSet, idNamePairs));
        }

        return new ClassSetDomain(
                this.classValidator.getApiOperator(m.getOp()),
                set.isEmpty() ? null : set,
                m.getCId() == null ? null : idNamePairs.getClassIdNameMap().get(m.getCId())
        );
    }

}
