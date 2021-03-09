package cz.nikolaslada.reasoner.repository.model;

import com.mongodb.lang.Nullable;
import lombok.Getter;

@Getter
public class DefinitionModel {

    @Nullable
    private final Integer cId;
    @Nullable
    private final Integer pId;
    @Nullable
    private final String restrict;
    @Nullable
    private final ClassSetModel set;
    @Nullable
    private final Integer val;


    public DefinitionModel(
            @Nullable Integer cId,
            @Nullable Integer pId,
            @Nullable String restrict,
            @Nullable ClassSetModel set,
            @Nullable Integer val
    ) {
        this.cId = cId;
        this.pId = pId;
        this.restrict = restrict;
        this.set = set;
        this.val = val;
    }

}
