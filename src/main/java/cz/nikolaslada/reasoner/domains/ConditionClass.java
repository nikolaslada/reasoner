package cz.nikolaslada.reasoner.domains;

import lombok.Getter;
import java.util.List;

@Getter
public class ConditionClass implements ConditionComposite {

    private final String name;
    private final List<ConditionComposite> set;

    public ConditionClass(String name, List<ConditionComposite> set) {
        this.name = name;
        this.set = set;
    }

}
