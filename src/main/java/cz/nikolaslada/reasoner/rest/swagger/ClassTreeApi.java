package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.rest.swagger.domains.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class ClassTreeApi {

    @GetMapping("/class-tree/{ontologyId}")
    public ClassTree getClassTree(@PathVariable int ontologyId) {
        List<ClassTreeNode> primitiveClassAList = Arrays.asList(
                new ClassTreeNode(16, "PrimitiveClassAA", Arrays.asList())
        );
        List<ClassTreeNode> primitiveClassList = Arrays.asList(
                new ClassTreeNode(15, "PrimitiveClassA", primitiveClassAList),
                new ClassTreeNode(15, "PrimitiveClassB", Arrays.asList())
        );
        List<ClassTreeNode> ModifierClassMAList = Arrays.asList(
                new ClassTreeNode(21, "ModifierClassMOptionI", Arrays.asList()),
                new ClassTreeNode(22, "ModifierClassMOptionJ", Arrays.asList())
        );
        List<ClassTreeNode> modifierClassList = Arrays.asList(
                new ClassTreeNode(20, "ModifierClassM", ModifierClassMAList)
        );
        List<ClassTreeNode> definableClassList = Arrays.asList(
                new ClassTreeNode(18, "DefinableClassX", Arrays.asList()),
                new ClassTreeNode(19, "DefinableClassY", Arrays.asList())
        );
        List<ClassTreeNode> rootList = Arrays.asList(
                new ClassTreeNode(11, "PrimitiveClass", primitiveClassList),
                new ClassTreeNode(12, "ModifierClass", modifierClassList),
                new ClassTreeNode(13, "DefinableClass", definableClassList),
                new ClassTreeNode(14, "TestClass", Arrays.asList())
        );
        ClassTreeNode tree = new ClassTreeNode(1, "owl:Thing", rootList);
        return new ClassTree(
                12,
                tree
        );
    }

}
