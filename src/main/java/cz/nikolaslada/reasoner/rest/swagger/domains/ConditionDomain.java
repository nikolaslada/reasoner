package cz.nikolaslada.reasoner.rest.swagger.domains;

import lombok.Getter;
import java.util.List;

@Getter
public class ConditionDomain {

    private final String type;
    private final String op;
    private final List<ConditionDomain> set;
    private final String name;
    private final String restriction;
    private final Integer val;


    public ConditionDomain(
            String type,
            String op,
            List<ConditionDomain> set,
            String name,
            String restriction,
            Integer val
    ) {
        super();
        this.type = type;
        this.op = op;
        this.set = set;
        this.name = name;
        this.restriction = restriction;
        this.val = val;
    }

}
