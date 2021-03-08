package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.domains.IdNamePairsDomain;
import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.repository.model.ConditionModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.ConditionDomain;
import cz.nikolaslada.reasoner.validators.ConditionValidator;
import cz.nikolaslada.reasoner.validators.PropertyValidator;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static cz.nikolaslada.reasoner.repository.identifiers.ConditionTypeId.*;
import static cz.nikolaslada.reasoner.rest.swagger.identifiers.ConditionTypeId.*;

@Component
public class ConditionFactory {

    private final ConditionValidator conditionValidator;
    private final PropertyValidator propertyValidator;


    public ConditionFactory(ConditionValidator conditionValidator, PropertyValidator propertyValidator) {
        this.conditionValidator = conditionValidator;
        this.propertyValidator = propertyValidator;
    }

    public ConditionModel createModel(ConditionDomain domain, NameIdPairsDomain nameIdPairs) throws Exception {
        List<ConditionModel> set = new ArrayList<>();
        for (ConditionDomain cD : domain.getSet()) {
            set.add(this.createModel(cD, nameIdPairs));
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

    public ConditionDomain createDomain(ConditionModel model, IdNamePairsDomain idNamePairs) throws Exception {
        List<ConditionDomain> set = new ArrayList<>();
        for (ConditionModel cM : model.getSet()) {
            set.add(this.createDomain(cM, idNamePairs));
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

}
