package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.repository.model.ClassSetModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.ClassSetDomain;
import cz.nikolaslada.reasoner.rest.swagger.error.BadRequestBuilder;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.validators.ClassValidator;
import cz.nikolaslada.reasoner.validators.PropertyValidator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit tests for ClassNodeFactory")
public class ClassNodeFactoryTest {

    public ClassValidator c = new ClassValidator();
    public PropertyValidator p = new PropertyValidator();
    public ClassNodeFactory f = new ClassNodeFactory(this.c, this.p);

    @DisplayName("Create of ClassSetModel test")
    @Test
    void createLinkModelTest() {
        HashMap<String, Integer> classNameIdMap = new HashMap<>();
        HashMap<String, Integer> propertyNameIdMap = new HashMap<>();
        classNameIdMap.put("TestClassA", 1);
        classNameIdMap.put("TestClassB", 22);
        classNameIdMap.put("TestClassC", 333);
        classNameIdMap.put("TestClassD", 4444);
        NameIdPairsDomain nameIdPairsDomain = new NameIdPairsDomain(classNameIdMap, propertyNameIdMap);

        ClassSetDomain d1 = new ClassSetDomain(
                "or",
                Arrays.asList(
                        new ClassSetDomain(
                                null,
                                null,
                                "TestClassA"
                        ),
                        new ClassSetDomain(
                                null,
                                null,
                                "TestClassB"
                        ),
                        new ClassSetDomain(
                                "and",
                                Arrays.asList(
                                        new ClassSetDomain(
                                                null,
                                                null,
                                                "TestClassC"
                                        ),
                                        new ClassSetDomain(
                                                null,
                                                null,
                                                "TestClassD"
                                        )
                                ),
                                null
                        )
                ),
                null
        );

        BadRequestBuilder b1 = new BadRequestBuilder();
        ClassSetModel m1 = f.createClassSetModel(d1, nameIdPairsDomain, b1, 0);

        assertEquals("o", m1.getOp());
        assertEquals(null, m1.getCId());

        assertEquals(null, m1.getSet().get(0).getOp());
        assertEquals(null, m1.getSet().get(0).getSet());
        assertEquals(1, m1.getSet().get(0).getCId());
        assertEquals(null, m1.getSet().get(1).getOp());
        assertEquals(null, m1.getSet().get(1).getSet());
        assertEquals(22, m1.getSet().get(1).getCId());
        assertEquals("a", m1.getSet().get(2).getOp());
        assertEquals(null, m1.getSet().get(2).getCId());

        assertEquals(null, m1.getSet().get(2).getSet().get(0).getOp());
        assertEquals(null, m1.getSet().get(2).getSet().get(0).getSet());
        assertEquals(333, m1.getSet().get(2).getSet().get(0).getCId());
        assertEquals(null, m1.getSet().get(2).getSet().get(1).getOp());
        assertEquals(null, m1.getSet().get(2).getSet().get(1).getSet());
        assertEquals(4444, m1.getSet().get(2).getSet().get(1).getCId());


        ClassSetDomain d2 = new ClassSetDomain(
                null,
                null,
                "TestClassA"
        );

        BadRequestBuilder b2 = new BadRequestBuilder();
        ClassSetModel m2 = f.createClassSetModel(d2, nameIdPairsDomain, b2, 0);

        assertEquals(null, m2.getOp());
        assertEquals(null, m2.getSet());
        assertEquals(1, m2.getCId());
    }

    @DisplayName("Create of ClassSetModel exception test")
    @Test
    void createClassSetModelExceptionTestA() {
        HashMap<String, Integer> classNameIdMap = new HashMap<>();
        HashMap<String, Integer> propertyNameIdMap = new HashMap<>();
        classNameIdMap.put("TestClassA", 1);
        NameIdPairsDomain nameIdPairsDomain = new NameIdPairsDomain(classNameIdMap, propertyNameIdMap);

        ClassSetDomain d1 = new ClassSetDomain(
                null,
                // because op is null set will not processed
                Arrays.asList(
                        new ClassSetDomain(
                                "and",
                                null,
                                "TestClassA"
                        )
                ),
                null
        );

        BadRequestBuilder b1 = new BadRequestBuilder();

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            f.createClassSetModel(d1, nameIdPairsDomain, b1, 0);
        });

        assertEquals(2, e.getErrorList().size());

        assertTrue(e.getErrorList().get(0).getMessage().contains(ClassNodeFactory.DEFINITION_SET_OP_CLASS));
        assertTrue(e.getErrorList().get(1).getMessage().contains(ClassNodeFactory.DEFINITION_SET_OP_SET));
    }

    @DisplayName("Create of ClassSetModel exception test")
    @Test
    void createClassSetModelExceptionTestB() {
        HashMap<String, Integer> classNameIdMap = new HashMap<>();
        HashMap<String, Integer> propertyNameIdMap = new HashMap<>();
        classNameIdMap.put("TestClassA", 1);
        NameIdPairsDomain nameIdPairsDomain = new NameIdPairsDomain(classNameIdMap, propertyNameIdMap);

        ClassSetDomain d1 = new ClassSetDomain(
                "and",
                null,
                "TestClassA"
        );

        BadRequestBuilder b1 = new BadRequestBuilder();

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            f.createClassSetModel(d1, nameIdPairsDomain, b1, 0);
        });

        assertEquals(2, e.getErrorList().size());

        assertTrue(e.getErrorList().get(0).getMessage().contains(ClassNodeFactory.DEFINITION_SET_OP_CLASS));
        assertTrue(e.getErrorList().get(0).getData().get(0).contains("and"));
        assertTrue(e.getErrorList().get(0).getData().get(1).contains("TestClassA"));
        assertTrue(e.getErrorList().get(1).getMessage().contains(ClassNodeFactory.DEFINITION_SET_OP_SET));
    }

    @DisplayName("Create of ClassSetModel exception test")
    @Test
    void createClassSetModelExceptionTestC() {
        HashMap<String, Integer> classNameIdMap = new HashMap<>();
        HashMap<String, Integer> propertyNameIdMap = new HashMap<>();
        classNameIdMap.put("TestClassA", 1);
        NameIdPairsDomain nameIdPairsDomain = new NameIdPairsDomain(classNameIdMap, propertyNameIdMap);

        ClassSetDomain d1 = new ClassSetDomain(
                "or",
                new ArrayList<>(),
                "TestClassA"
        );

        BadRequestBuilder b1 = new BadRequestBuilder();

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            f.createClassSetModel(d1, nameIdPairsDomain, b1, 0);
        });

        assertEquals(2, e.getErrorList().size());

        assertTrue(e.getErrorList().get(0).getMessage().contains(ClassNodeFactory.DEFINITION_SET_OP_CLASS));
        assertTrue(e.getErrorList().get(1).getMessage().contains(ClassNodeFactory.DEFINITION_SET_OP_SET));
    }

}
