package cz.nikolaslada.reasoner.repository.model;

import com.mongodb.lang.Nullable;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Getter
public class ConditionModel implements ConditionComposite {

    private final String type;
    @Nullable
    private final String op;
    @Nullable
    private final List<ConditionModel> set;
    @DBRef
    @Nullable
    private final Integer classId;
    @DBRef
    @Nullable
    private final Integer propertyId;
    @Nullable
    private final String restrict;
    @Nullable
    private final Integer val;


    public ConditionModel(
            String type,
            @Nullable String op,
            @Nullable List<ConditionModel> set,
            @Nullable Integer classId,
            @Nullable Integer propertyId,
            @Nullable String restrict,
            @Nullable Integer val
    ) {
        this.type = type;
        this.op = op;
        this.set = set;
        this.classId = classId;
        this.propertyId = propertyId;
        this.restrict = restrict;
        this.val = val;
    }

}
