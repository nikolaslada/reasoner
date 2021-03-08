package cz.nikolaslada.reasoner.domains;

import lombok.Getter;

import java.util.HashMap;

@Getter
public class IdNamePairsDomain {

    private final HashMap<Integer, String> classIdNameMap;
    private final HashMap<Integer, String> propertyIdNameMap;


    public IdNamePairsDomain(
            HashMap<Integer, String> classIdNameMap,
            HashMap<Integer, String> propertyIdNameMap
    ) {
        this.classIdNameMap = classIdNameMap;
        this.propertyIdNameMap = propertyIdNameMap;
    }

}
