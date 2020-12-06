package cz.nikolaslada.reasoner.rest.swagger.domains;

import java.util.List;

public class ClassTreeNode {
    private int id;
    private String name;
    private List<ClassTreeNode> nodes;

    public ClassTreeNode(int id, String name, List<ClassTreeNode> nodes) {
        super();
        this.id = id;
        this.name = name;
        this.nodes = nodes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<ClassTreeNode> getNodes() {
        return nodes;
    }

}
