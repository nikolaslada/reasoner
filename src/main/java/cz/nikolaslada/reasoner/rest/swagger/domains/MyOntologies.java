package cz.nikolaslada.reasoner.rest.swagger.domains;

import java.util.List;

public class MyOntologies {
    private int offset;
    private int count;
    private int totalCount;
    private List<MyOntologyItem> ontologyList;

    public MyOntologies(int offset, int count, int totalCount, List<MyOntologyItem> ontologyList) {
        super();
        this.offset = offset;
        this.count = count;
        this.totalCount = totalCount;
        this.ontologyList = ontologyList;
    }

    public int getOffset() {
        return offset;
    }

    public int getCount() {
        return count;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public List<MyOntologyItem> getOntologyList() {
        return ontologyList;
    }

}
