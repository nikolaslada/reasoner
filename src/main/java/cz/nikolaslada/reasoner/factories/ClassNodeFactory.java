package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.domains.IdNamePairsDomain;
import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.repository.model.*;
import cz.nikolaslada.reasoner.rest.swagger.domains.ConditionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.DefinitionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.ClassSetDomain;
import cz.nikolaslada.reasoner.rest.swagger.error.BadRequestBuilder;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.InternalException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
import cz.nikolaslada.reasoner.validators.ClassValidator;
import cz.nikolaslada.reasoner.validators.PropertyValidator;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cz.nikolaslada.reasoner.repository.identifiers.ConditionTypeId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.ConditionTypeId.*;

@Component
public class ClassNodeFactory {

    private static final String CONDITION_SET = "Condition with type 'set' is not valid!";;
    private static final String CONDITION_CLASS = "Condition with type 'class' is not valid!";
    private static final String CONDITION_PROPERTY = "Condition with type 'property' is not valid!";
    private static final String CONDITION_NOT = "Condition with type 'not' is not valid!";
    public static final String DEFINITION_SET_OP_CLASS = "Set in Definition must not contain null or non-null values for both 'op' and 'class' attributes!";
    public static final String DEFINITION_SET_OP_SET = "Set in Definition must contain null or non-null values for both 'op' and 'set' attributes!";

    private final ClassValidator classValidator;
    private final PropertyValidator propertyValidator;


    public ClassNodeFactory(ClassValidator classValidator, PropertyValidator propertyValidator) {
        this.classValidator = classValidator;
        this.propertyValidator = propertyValidator;
    }

    public ClassNodeModel createClassNodeModel(
            Integer id,
            Integer ontologyId,
            String name,
            ZonedDateTime createdAt,
            ZonedDateTime updatedAt,
            List<TranslationModel> translationList,
            LinkModel link,
            List<DefinitionDomain> definitionList,
            ConditionDomain condition,
            NameIdPairsDomain nameIdPairsDomain
    ) throws BadRequestException {
        BadRequestBuilder badRequestBuilder = new BadRequestBuilder();
        ClassNodeModel classNodeModel = new ClassNodeModel(
                id,
                ontologyId,
                name,
                createdAt,
                updatedAt,
                translationList,
                link,
                this.createDefinitionModelList(definitionList, nameIdPairsDomain, badRequestBuilder),
                this.createConditionModel(condition, nameIdPairsDomain, badRequestBuilder)
        );

        if (!badRequestBuilder.isEmpty()) {
            throw badRequestBuilder.build();
        }

        return classNodeModel;
    }

    public ConditionModel createConditionModel(ConditionDomain domain, NameIdPairsDomain nameIdPairs, BadRequestBuilder badRequestBuilder) throws ErrorException {
        List<ConditionModel> set = new ArrayList<>();
        if (domain.getSet() != null) {
            for (ConditionDomain cD : domain.getSet()) {
                set.add(this.createConditionModel(cD, nameIdPairs, badRequestBuilder));
            }
        }

        switch (domain.getType()) {
            case SET_API:
                if (set.isEmpty() || domain.getOp() == null || domain.getName() != null || domain.getRestriction() != null || domain.getVal() != null) {
                    throw badRequestBuilder
                            .addErrorItem(CONDITION_SET)
                            .build();
                }

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
                if (!set.isEmpty() || domain.getOp() != null || domain.getName() == null || domain.getRestriction() != null || domain.getVal() != null) {
                    throw badRequestBuilder
                            .addErrorItem(CONDITION_CLASS)
                            .build();
                }

                Integer classId = nameIdPairs.getClassIdNameMap().get(domain.getName());
                if (classId == null) {
                    throw  NotFoundException
                            .builder()
                            .message("There is no Class with name: ")
                            .data(Arrays.asList(domain.getName()))
                            .build();
                }

                return new ConditionModel(
                        CLASS_DB,
                        null,
                        set,
                        classId,
                        null,
                        null,
                        null
                );
            case PROPERTY_API:
                String dbRestrict = this.propertyValidator.getDbRestriction(domain.getRestriction());
                if (
                        domain.getOp() != null
                        || domain.getName() == null
                        || (
                                this.propertyValidator.isDbRestrictionForClass(dbRestrict)
                                && (set.isEmpty() || domain.getVal() != null)
                        )
                        || (
                                this.propertyValidator.isDbRestrictionForValue(dbRestrict)
                                && (!set.isEmpty() || domain.getVal() == null)
                        )
                ) {
                    throw badRequestBuilder
                            .addErrorItem(CONDITION_PROPERTY)
                            .build();
                }

                Integer propertyId = nameIdPairs.getPropertyIdNameMap().get(domain.getName());
                if (propertyId == null) {
                    throw  NotFoundException
                            .builder()
                            .message("There is no Property with name: ")
                            .data(Arrays.asList(domain.getName()))
                            .build();
                }

                return new ConditionModel(
                        PROPERTY_DB,
                        null,
                        set,
                        null,
                        propertyId,
                        dbRestrict,
                        domain.getVal()
                );
            case TYPE_NOT_API:
                if (set.isEmpty() || domain.getOp() != null || domain.getName() != null || domain.getRestriction() != null || domain.getVal() != null) {
                    throw badRequestBuilder
                            .addErrorItem(CONDITION_NOT)
                            .build();
                }

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
                throw badRequestBuilder
                        .addErrorItem("Not supported id of condition type '%c'.", domain.getType())
                        .build();
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
                throw InternalException
                        .builder()
                        .message("Not supported id of condition type '%c'.")
                        .data(Arrays.asList(model.getType()))
                        .build();
        }
    }

    public List<DefinitionModel> createDefinitionModelList(List<DefinitionDomain> list, NameIdPairsDomain nameIdPairs, BadRequestBuilder badRequestBuilder) throws BadRequestException {
        List<DefinitionModel> set = new ArrayList<>();
        if (list != null) {
            for (DefinitionDomain d : list) {
                set.add(this.createDefinitionModel(d, nameIdPairs, badRequestBuilder));
            }
        }

        return set;
    }

    public DefinitionModel createDefinitionModel(DefinitionDomain d, NameIdPairsDomain nameIdPairs, BadRequestBuilder badRequestBuilder) throws BadRequestException {
        return new DefinitionModel(
                d.getClassName() == null ? null : nameIdPairs.getClassIdNameMap().get(d.getClassName()),
                d.getProperty() == null ? null : nameIdPairs.getPropertyIdNameMap().get(d.getProperty()),
                this.propertyValidator.getDbRestriction(d.getRestriction()),
                d.getSet() == null ? null : this.createClassSetModel(d.getSet(), nameIdPairs, badRequestBuilder),
                d.getVal()
        );
    }

    public ClassSetModel createClassSetModel(
            ClassSetDomain d,
            NameIdPairsDomain nameIdPairs,
            BadRequestBuilder badRequestBuilder
    ) throws BadRequestException {
        String dbOp;
        Integer cId;
        List<ClassSetModel> set = new ArrayList<>();

        if (d.getOp() == null) {
            dbOp = null;
            cId = nameIdPairs.getClassIdNameMap().get(d.getName());

            if (d.getName() == null) {
                badRequestBuilder.addErrorItem(DEFINITION_SET_OP_CLASS);
            }

            if (d.getSet() != null && !d.getSet().isEmpty()) {
                badRequestBuilder.addErrorItem(DEFINITION_SET_OP_SET);
            }
        } else {
            dbOp = this.classValidator.getDbOperator(d.getOp());
            cId = null;

            if (d.getName() != null) {
                badRequestBuilder.addErrorItem(DEFINITION_SET_OP_CLASS, Arrays.asList(d.getOp(), d.getName()));
            }

            if (d.getSet() == null || d.getSet().isEmpty()) {
                badRequestBuilder.addErrorItem(DEFINITION_SET_OP_SET);
            } else {
                for (ClassSetDomain classSet : d.getSet()) {
                    set.add(this.createClassSetModel(classSet, nameIdPairs, badRequestBuilder));
                }
            }
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
                m.getOp() == null ? null : this.classValidator.getApiOperator(m.getOp()),
                set,
                m.getCId() == null ? null : idNamePairs.getClassIdNameMap().get(m.getCId())
        );
    }

}
