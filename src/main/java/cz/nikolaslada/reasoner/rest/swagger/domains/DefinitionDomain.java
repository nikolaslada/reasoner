package cz.nikolaslada.reasoner.rest.swagger.domains;

import cz.nikolaslada.reasoner.rest.swagger.domains.request.ClassSetDomain;
import lombok.Getter;

@Getter
public class DefinitionDomain {

    private final String className;
    private final String property;
    private final String restriction;
    private final ClassSetDomain set;
    private final Integer val;


    public DefinitionDomain(String className, String property, String restriction, ClassSetDomain set, Integer val) {
        super();
        this.className = className;
        this.property = property;
        this.restriction = restriction;
        this.set = set;
        this.val = val;
    }

}
