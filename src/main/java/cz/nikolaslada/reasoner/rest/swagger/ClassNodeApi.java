package cz.nikolaslada.reasoner.rest.swagger;

import cz.nikolaslada.reasoner.mappers.ClassNodeMapper;
import cz.nikolaslada.reasoner.repository.model.ClassNodeModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.NewClassDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.response.ClassDetail;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.ConflictException;
import cz.nikolaslada.reasoner.services.ClassNodeService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClassNodeApi {

    private final ClassNodeService service;


    public ClassNodeApi(ClassNodeService service) {
        this.service = service;
    }

    @GetMapping("/class-node/{id}")
    public ClassDetail getOntology(@PathVariable Integer id) {
        ClassNodeModel classNodeModel = this.service.getById(id);
        return ClassNodeMapper.INSTANCE.classNodeModelToClassDetail(classNodeModel);
    }

    @PostMapping(
            value = "/class-node",
            produces = { "application/json" },
            consumes = { "application/json" }
    )
    public ClassDetail post(@RequestBody NewClassDomain newClassDomain) throws ConflictException {
        ClassNodeModel classNodeModel = this.service.create(newClassDomain);
        return ClassNodeMapper.INSTANCE.classNodeModelToClassDetail(classNodeModel);
    }

}
