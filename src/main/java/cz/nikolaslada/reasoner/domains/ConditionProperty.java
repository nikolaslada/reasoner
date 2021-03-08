package cz.nikolaslada.reasoner.domains;

import lombok.Getter;
import java.util.List;

@Getter
public class ConditionProperty implements ConditionComposite {

    private final String name;
    private final String restriction;
    private final Integer value;
    private final List<ConditionComposite> set;


    public ConditionProperty(String name, String restriction, Integer value, List<ConditionComposite> set) {
        this.name = name;
        this.restriction = restriction;
        this.value = value;
        this.set = set;
    }

}
