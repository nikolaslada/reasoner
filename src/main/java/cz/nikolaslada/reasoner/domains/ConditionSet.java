package cz.nikolaslada.reasoner.domains;

import lombok.Getter;
import java.util.List;

@Getter
public class ConditionSet implements ConditionComposite {

    private final String operator;
    private final List<ConditionComposite> set;


    public ConditionSet(String operator, List<ConditionComposite> set) {
        this.operator = operator;
        this.set = set;
    }

}
