package cz.nikolaslada.reasoner.rest.swagger.domains;

import lombok.Getter;
import java.util.List;

@Getter
public class ConditionDomain {

    private final String type;
    private final String op;
    private final ConditionDomain set;
    private final List<ConditionDomain> list;
    private final String name;
    private final String restriction;
    private final Integer val;


    public ConditionDomain(
            String type,
            String op,
            ConditionDomain set,
            List<ConditionDomain> list,
            String name,
            String restriction,
            Integer val
    ) {
        super();
        this.type = type;
        this.op = op;
        this.set = set;
        this.list = list;
        this.name = name;
        this.restriction = restriction;
        this.val = val;
    }

}
