package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.domains.IdNamePairsDomain;
import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.repository.model.ClassNodeModel;
import cz.nikolaslada.reasoner.repository.model.Property;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class DomainFactory {

    public IdNamePairsDomain createIdNamePairs(
            List<ClassNodeModel> classNodeModelList,
            List<Property> propertyList
    ) {
        HashMap<Integer, String> classIdNameMap = new HashMap<>();
        HashMap<Integer, String> propertyIdNameMap = new HashMap<>();

        for (ClassNodeModel classNodeModel : classNodeModelList) {
            classIdNameMap.put(classNodeModel.getId(), classNodeModel.getName());
        }

        for (Property property : propertyList) {
            propertyIdNameMap.put(property.getId(), property.getName());
        }

        return new IdNamePairsDomain(classIdNameMap, propertyIdNameMap);
    }

    public NameIdPairsDomain createNameIdPairs(
            List<ClassNodeModel> classNodeModelList,
            List<Property> propertyList
    ) {
        HashMap<String, Integer> classNameIdMap = new HashMap<>();
        HashMap<String, Integer> propertyNameIdMap = new HashMap<>();

        for (ClassNodeModel classNodeModel : classNodeModelList) {
            classNameIdMap.put(classNodeModel.getName(), classNodeModel.getId());
        }

        for (Property property : propertyList) {
            propertyNameIdMap.put(property.getName(), property.getId());
        }

        return new NameIdPairsDomain(classNameIdMap, propertyNameIdMap);
    }

}
