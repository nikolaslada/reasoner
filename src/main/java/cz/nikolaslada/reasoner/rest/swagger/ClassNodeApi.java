package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.mappers.ClassNodeMapper;
import cz.nikolaslada.reasoner.repository.model.ClassNode;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewClassNode;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.ClassDetail;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ErrorException;
import cz.nikolaslada.reasoner.services.ClassNodeService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClassNodeApi {

    private final ClassNodeService service;


    public ClassNodeApi(ClassNodeService service) {
        this.service = service;
    }

    @GetMapping("/class-node/{id}")
    public ClassDetail getOntology(@PathVariable int id) {
        ClassNode classNode = this.service.getById(id);
        return ClassNodeMapper.INSTANCE.classNodeModelToClassDetail(classNode);
    }

    @PostMapping(
            value = "/class-node",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ClassDetail post(@RequestBody NewClassNode newClassNode) throws ErrorException {
        ClassNode classNode = this.service.create(newClassNode);
        return ClassNodeMapper.INSTANCE.classNodeModelToClassDetail(classNode);
    }

}
