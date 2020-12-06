package cz.nikolaslada.reasoner.rest.swagger.domains;

public class ClassTree {
    private int count;
    private ClassTreeNode tree;

    public ClassTree(int count, ClassTreeNode tree) {
        super();
        this.count = count;
        this.tree = tree;
    }

    public int getCount() {
        return count;
    }

    public ClassTreeNode getTreeList() {
        return tree;
    }

}
