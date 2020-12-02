package cz.nikolaslada.reasoner.rest.swagger.domains;

public class OntologyDetail {
    private int id;
    private String name;
    private int classCount;
    private int propertyCount;
    private int individualCount;

    public OntologyDetail(int id, String name, int classCount, int propertyCount, int individualCount) {
        super();
        this.id = id;
        this.name = name;
        this.classCount = classCount;
        this.propertyCount = propertyCount;
        this.individualCount = individualCount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getClassCount() {
        return classCount;
    }

    public int getPropertyCount() {
        return propertyCount;
    }

    public int getIndividualCount() {
        return individualCount;
    }

}
