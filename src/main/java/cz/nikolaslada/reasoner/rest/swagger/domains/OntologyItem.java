package cz.nikolaslada.reasoner.rest.swagger.domains;

public class OntologyItem {
    private int id;
    private String name;
    private int classCount;
    private int propertyCount;
    private int individualCount;
    private User owner;

    public OntologyItem(int id, String name, int classCount, int propertyCount, int individualCount, User owner) {
        super();
        this.id = id;
        this.name = name;
        this.classCount = classCount;
        this.propertyCount = propertyCount;
        this.individualCount = individualCount;
        this.owner = owner;
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

    public User getOwner() {
        return owner;
    }

}
