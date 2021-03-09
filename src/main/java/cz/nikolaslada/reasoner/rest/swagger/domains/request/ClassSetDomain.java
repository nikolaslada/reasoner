package cz.nikolaslada.reasoner.rest.swagger.domains.request;

import lombok.Getter;

import java.util.List;

@Getter
public class ClassSetDomain {

    private final String op;
    private final List<ClassSetDomain> set;
    private final String name;


    public ClassSetDomain(String op, List<ClassSetDomain> set, String name) {
        super();
        this.op = op;
        this.set = set;
        this.name = name;
    }

}
