package cz.nikolaslada.reasoner.domains;

import lombok.Getter;
import java.util.List;

@Getter
public class ConditionSet implements ConditionComposite {

    private final Operator operator;
    private final List<ConditionComposite> set;

    public ConditionSet(Operator operator, List<ConditionComposite> set) {
        this.operator = operator;
        this.set = set;
    }

}
