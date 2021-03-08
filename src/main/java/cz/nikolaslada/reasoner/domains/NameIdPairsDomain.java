package cz.nikolaslada.reasoner.domains;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class NameIdPairsDomain {

    private final HashMap<String, Integer> classIdNameMap;
    private final HashMap<String, Integer> propertyIdNameMap;


    public NameIdPairsDomain(
            HashMap<String, Integer> classIdNameMap,
            HashMap<String, Integer> propertyIdNameMap
    ) {
        this.classIdNameMap = classIdNameMap;
        this.propertyIdNameMap = propertyIdNameMap;
    }

}
