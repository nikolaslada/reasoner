package cz.nikolaslada.reasoner.repository.model;

import com.mongodb.lang.Nullable;
import lombok.Getter;

import java.util.List;

@Getter
public class ClassSetModel {

    private final String op;
    @Nullable
    private final List<ClassSetModel> set;
    @Nullable
    private final Integer cId;


    public ClassSetModel(String op, List<ClassSetModel> set, Integer cId) {
        this.op = op;
        this.set = set;
        this.cId = cId;
    }

}
