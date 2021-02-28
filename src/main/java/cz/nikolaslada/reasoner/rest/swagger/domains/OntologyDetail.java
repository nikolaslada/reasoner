package cz.nikolaslada.reasoner.rest.swagger.domains;

public class OntologyDetail {

    private Integer id;
    private String name;
    private Integer classCount;
    private Integer propertyCount;
    private Integer individualCount;

    public OntologyDetail(Integer id, String name, Integer classCount, Integer propertyCount, Integer individualCount) {
        super();
        this.id = id;
        this.name = name;
        this.classCount = classCount;
        this.propertyCount = propertyCount;
        this.individualCount = individualCount;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getClassCount() {
        return classCount;
    }

    public Integer getPropertyCount() {
        return propertyCount;
    }

    public Integer getIndividualCount() {
        return individualCount;
    }

}
