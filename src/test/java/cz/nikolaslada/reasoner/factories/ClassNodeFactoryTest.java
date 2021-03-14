package cz.nikolaslada.reasoner.factories;

import cz.nikolaslada.reasoner.domains.IdNamePairsDomain;
import cz.nikolaslada.reasoner.domains.NameIdPairsDomain;
import cz.nikolaslada.reasoner.repository.model.ClassSetModel;
import cz.nikolaslada.reasoner.repository.model.ConditionModel;
import cz.nikolaslada.reasoner.rest.swagger.domains.ConditionDomain;
import cz.nikolaslada.reasoner.rest.swagger.domains.request.ClassSetDomain;
import cz.nikolaslada.reasoner.rest.swagger.error.BadRequestBuilder;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.BadRequestException;
import cz.nikolaslada.reasoner.rest.swagger.exceptions.NotFoundException;
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
    void createClassSetModelTest() {
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
                                new ArrayList<>(),
                                "TestClassB"
                        ),
                        new ClassSetDomain(
                                "and",
                                Arrays.asList(
                                        new ClassSetDomain(
                                                null,
                                                new ArrayList<>(),
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
        ClassSetModel m1 = f.createClassSetModel(d1, nameIdPairsDomain, b1);

        assertEquals("o", m1.getOp());
        assertEquals(null, m1.getCId());

        assertEquals(null, m1.getSet().get(0).getOp());
        assertEquals(0, m1.getSet().get(0).getSet().size());
        assertEquals(1, m1.getSet().get(0).getCId());
        assertEquals(null, m1.getSet().get(1).getOp());
        assertEquals(0, m1.getSet().get(1).getSet().size());
        assertEquals(22, m1.getSet().get(1).getCId());
        assertEquals("a", m1.getSet().get(2).getOp());
        assertEquals(null, m1.getSet().get(2).getCId());

        assertEquals(null, m1.getSet().get(2).getSet().get(0).getOp());
        assertEquals(0, m1.getSet().get(2).getSet().get(0).getSet().size());
        assertEquals(333, m1.getSet().get(2).getSet().get(0).getCId());
        assertEquals(null, m1.getSet().get(2).getSet().get(1).getOp());
        assertEquals(0, m1.getSet().get(2).getSet().get(1).getSet().size());
        assertEquals(4444, m1.getSet().get(2).getSet().get(1).getCId());


        ClassSetDomain d2 = new ClassSetDomain(
                null,
                null,
                "TestClassA"
        );

        BadRequestBuilder b2 = new BadRequestBuilder();
        ClassSetModel m2 = f.createClassSetModel(d2, nameIdPairsDomain, b2);

        assertEquals(null, m2.getOp());
        assertEquals(0, m2.getSet().size());
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
        f.createClassSetModel(d1, nameIdPairsDomain, b1);

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            throw b1.build();
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
        f.createClassSetModel(d1, nameIdPairsDomain, b1);

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            throw b1.build();
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
        f.createClassSetModel(d1, nameIdPairsDomain, b1);

        BadRequestException e = assertThrows(BadRequestException.class, () -> {
            throw b1.build();
        });

        assertEquals(2, e.getErrorList().size());

        assertTrue(e.getErrorList().get(0).getMessage().contains(ClassNodeFactory.DEFINITION_SET_OP_CLASS));
        assertTrue(e.getErrorList().get(1).getMessage().contains(ClassNodeFactory.DEFINITION_SET_OP_SET));
    }

    @DisplayName("Create of ClassSetDomain test")
    @Test
    void createClassSetDomainTest() {
        HashMap<Integer, String> classIdNameMap = new HashMap<>();
        HashMap<Integer, String> propertyIdNameMap = new HashMap<>();
        classIdNameMap.put(1, "TestClassA");
        classIdNameMap.put(22, "TestClassB");
        classIdNameMap.put(333, "TestClassC");
        classIdNameMap.put(4444, "TestClassD");
        IdNamePairsDomain idNamePairsDomain = new IdNamePairsDomain(classIdNameMap, propertyIdNameMap);

        ClassSetModel m1 = new ClassSetModel(
                "a",
                Arrays.asList(
                        new ClassSetModel(
                                null,
                                new ArrayList<>(),
                                1
                        ),
                        new ClassSetModel(
                                null,
                                new ArrayList<>(),
                                22
                        ),
                        new ClassSetModel(
                                "o",
                                Arrays.asList(
                                        new ClassSetModel(
                                                null,
                                                new ArrayList<>(),
                                                333
                                        ),
                                        new ClassSetModel(
                                                null,
                                                new ArrayList<>(),
                                                4444
                                        )
                                ),
                                null
                        )
                ),
                null
        );

        ClassSetDomain d1 = f.createClassSetDomain(m1, idNamePairsDomain);

        assertEquals("and", d1.getOp());
        assertEquals(null, d1.getName());

        assertEquals(null, d1.getSet().get(0).getOp());
        assertEquals(0, d1.getSet().get(0).getSet().size());
        assertEquals("TestClassA", d1.getSet().get(0).getName());
        assertEquals(null, d1.getSet().get(1).getOp());
        assertEquals(0, d1.getSet().get(1).getSet().size());
        assertEquals("TestClassB", d1.getSet().get(1).getName());
        assertEquals("or", d1.getSet().get(2).getOp());
        assertEquals(null, d1.getSet().get(2).getName());

        assertEquals(null, d1.getSet().get(2).getSet().get(0).getOp());
        assertEquals(0, d1.getSet().get(2).getSet().get(0).getSet().size());
        assertEquals("TestClassC", d1.getSet().get(2).getSet().get(0).getName());
        assertEquals(null, d1.getSet().get(2).getSet().get(1).getOp());
        assertEquals(0, d1.getSet().get(2).getSet().get(1).getSet().size());
        assertEquals("TestClassD", d1.getSet().get(2).getSet().get(1).getName());


        ClassSetModel m2 = new ClassSetModel(
                null,
                new ArrayList<>(),
                1
        );

        ClassSetDomain d2 = f.createClassSetDomain(m2, idNamePairsDomain);

        assertEquals(null, d2.getOp());
        assertEquals(0, d2.getSet().size());
        assertEquals("TestClassA", d2.getName());
    }

    @DisplayName("Create of ConditionModel test")
    @Test
    void createTest() {
        HashMap<String, Integer> classNameIdMap = new HashMap<>();
        HashMap<String, Integer> propertyNameIdMap = new HashMap<>();
        classNameIdMap.put("TestClassA", 1);
        classNameIdMap.put("TestClassB", 22);
        classNameIdMap.put("TestClassC", 333);
        classNameIdMap.put("TestClassD", 4444);
        propertyNameIdMap.put("hasValueA", 13);
        propertyNameIdMap.put("hasValueB", 17);
        propertyNameIdMap.put("hasValueC", 18);
        NameIdPairsDomain nameIdPairsDomain = new NameIdPairsDomain(classNameIdMap, propertyNameIdMap);

        ConditionDomain d1 = new ConditionDomain(
                "set",
                "and",
                Arrays.asList(
                        new ConditionDomain(
                                "class",
                                null,
                                null,
                                "TestClassA",
                                null,
                                null
                        ),
                        new ConditionDomain(
                                "property",
                                null,
                                Arrays.asList(
                                        new ConditionDomain(
                                                "class",
                                                null,
                                                null,
                                                "TestClassB",
                                                null,
                                                null
                                        )
                                ),
                                "hasValueA",
                                "some",
                                null
                        ),
                        new ConditionDomain(
                                "property",
                                null,
                                Arrays.asList(
                                        new ConditionDomain(
                                                "set",
                                                "and",
                                                Arrays.asList(
                                                        new ConditionDomain(
                                                                "class",
                                                                null,
                                                                null,
                                                                "TestClassC",
                                                                null,
                                                                null
                                                        ),
                                                        new ConditionDomain(
                                                                "not",
                                                                null,
                                                                Arrays.asList(
                                                                        new ConditionDomain(
                                                                                "class",
                                                                                null,
                                                                                null,
                                                                                "TestClassD",
                                                                                null,
                                                                                null
                                                                        )
                                                                ),
                                                                null,
                                                                null,
                                                                null
                                                        )
                                                ),
                                                null,
                                                null,
                                                null
                                        )
                                ),
                                "hasValueB",
                                "only",
                                null
                        ),
                        new ConditionDomain(
                                "property",
                                null,
                                null,
                                "hasValueC",
                                "min",
                                2
                        )
                ),
                null,
                null,
                null
        );

        BadRequestBuilder b1 = new BadRequestBuilder();
        ConditionModel m1 = f.createConditionModel(d1, nameIdPairsDomain, b1);

        assertTrue(b1.isEmpty());

        assertEquals("s", m1.getType());
        assertEquals("a", m1.getOp());
        assertEquals(4, m1.getSet().size());
        assertEquals(null, m1.getClassId());
        assertEquals(null, m1.getPropertyId());
        assertEquals(null, m1.getRestrict());
        assertEquals(null, m1.getVal());

        ConditionModel m1a = m1.getSet().get(0);
        ConditionModel m1b = m1.getSet().get(1);
        ConditionModel m1c = m1.getSet().get(2);
        ConditionModel m1d = m1.getSet().get(3);

        assertEquals("c", m1a.getType());
        assertEquals(null, m1a.getOp());
        assertEquals(0, m1a.getSet().size());
        assertEquals(1, m1a.getClassId());
        assertEquals(null, m1a.getPropertyId());
        assertEquals(null, m1a.getRestrict());
        assertEquals(null, m1a.getVal());

        assertEquals("p", m1b.getType());
        assertEquals(null, m1b.getOp());
        assertEquals(1, m1b.getSet().size());
        assertEquals(null, m1b.getClassId());
        assertEquals(13, m1b.getPropertyId());
        assertEquals("s", m1b.getRestrict());
        assertEquals(null, m1b.getVal());

        ConditionModel m1b1 = m1b.getSet().get(0);

        assertEquals("c", m1b1.getType());
        assertEquals(null, m1b1.getOp());
        assertEquals(0, m1b1.getSet().size());
        assertEquals(22, m1b1.getClassId());
        assertEquals(null, m1b1.getPropertyId());
        assertEquals(null, m1b1.getRestrict());
        assertEquals(null, m1b1.getVal());

        assertEquals("p", m1c.getType());
        assertEquals(null, m1c.getOp());
        assertEquals(1, m1c.getSet().size());
        assertEquals(null, m1c.getClassId());
        assertEquals(17, m1c.getPropertyId());
        assertEquals("o", m1c.getRestrict());
        assertEquals(null, m1c.getVal());

        ConditionModel m1c1 = m1c.getSet().get(0);

        assertEquals("s", m1c1.getType());
        assertEquals("a", m1c1.getOp());
        assertEquals(2, m1c1.getSet().size());
        assertEquals(null, m1c1.getClassId());
        assertEquals(null, m1c1.getPropertyId());
        assertEquals(null, m1c1.getRestrict());
        assertEquals(null, m1c1.getVal());

        ConditionModel m1c1a = m1c1.getSet().get(0);
        ConditionModel m1c1b = m1c1.getSet().get(1);

        assertEquals("c", m1c1a.getType());
        assertEquals(null, m1c1a.getOp());
        assertEquals(0, m1c1a.getSet().size());
        assertEquals(333, m1c1a.getClassId());
        assertEquals(null, m1c1a.getPropertyId());
        assertEquals(null, m1c1a.getRestrict());
        assertEquals(null, m1c1a.getVal());

        assertEquals("n", m1c1b.getType());
        assertEquals(null, m1c1b.getOp());
        assertEquals(1, m1c1b.getSet().size());
        assertEquals(null, m1c1b.getClassId());
        assertEquals(null, m1c1b.getPropertyId());
        assertEquals(null, m1c1b.getRestrict());
        assertEquals(null, m1c1b.getVal());

        ConditionModel m1c1b1 = m1c1b.getSet().get(0);

        assertEquals("c", m1c1b1.getType());
        assertEquals(null, m1c1b1.getOp());
        assertEquals(0, m1c1b1.getSet().size());
        assertEquals(4444, m1c1b1.getClassId());
        assertEquals(null, m1c1b1.getPropertyId());
        assertEquals(null, m1c1b1.getRestrict());
        assertEquals(null, m1c1b1.getVal());

        assertEquals("p", m1d.getType());
        assertEquals(null, m1d.getOp());
        assertEquals(0, m1d.getSet().size());
        assertEquals(null, m1d.getClassId());
        assertEquals(18, m1d.getPropertyId());
        assertEquals("i", m1d.getRestrict());
        assertEquals(2, m1d.getVal());
    }

    @DisplayName("Create of ConditionModel exception test")
    @Test
    void createConditionModelExceptionTestSet() {
        HashMap<String, Integer> classNameIdMap = new HashMap<>();
        HashMap<String, Integer> propertyNameIdMap = new HashMap<>();
        classNameIdMap.put("TestClassA", 1);
        NameIdPairsDomain nameIdPairsDomain = new NameIdPairsDomain(classNameIdMap, propertyNameIdMap);

        ConditionDomain d1 = new ConditionDomain(
                "set",
                null,
                Arrays.asList(
                        new ConditionDomain(
                                "class",
                                null,
                                null,
                                "TestClassA",
                                null,
                                null
                        )
                ),
                null,
                null,
                null
        );

        BadRequestBuilder b1 = new BadRequestBuilder();

        BadRequestException e1 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d1, nameIdPairsDomain, b1);
            throw b1.build();
        });

        assertEquals(1, e1.getErrorList().size());

        ConditionDomain d2 = new ConditionDomain(
                "set",
                "and",
                null,
                null,
                null,
                null
        );

        BadRequestBuilder b2 = new BadRequestBuilder();

        BadRequestException e2 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d2, nameIdPairsDomain, b2);
            throw b2.build();
        });

        assertEquals(1, e2.getErrorList().size());

        ConditionDomain d3 = new ConditionDomain(
                "set",
                "and",
                Arrays.asList(
                        new ConditionDomain(
                                "class",
                                null,
                                null,
                                "TestClassA",
                                null,
                                null
                        )
                ),
                "TestClassA",
                null,
                null
        );

        BadRequestBuilder b3 = new BadRequestBuilder();

        BadRequestException e3 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d3, nameIdPairsDomain, b3);
            throw b3.build();
        });

        assertEquals(1, e3.getErrorList().size());

        ConditionDomain d4 = new ConditionDomain(
                "set",
                "and",
                Arrays.asList(
                        new ConditionDomain(
                                "class",
                                null,
                                null,
                                "TestClassA",
                                null,
                                null
                        )
                ),
                null,
                "only",
                null
        );

        BadRequestBuilder b4 = new BadRequestBuilder();

        BadRequestException e4 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d4, nameIdPairsDomain, b4);
            throw b4.build();
        });

        assertEquals(1, e4.getErrorList().size());

        ConditionDomain d5 = new ConditionDomain(
                "set",
                "and",
                Arrays.asList(
                        new ConditionDomain(
                                "class",
                                null,
                                null,
                                "TestClassA",
                                null,
                                null
                        )
                ),
                null,
                null,
                1
        );

        BadRequestBuilder b5 = new BadRequestBuilder();

        BadRequestException e5 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d5, nameIdPairsDomain, b5);
            throw b5.build();
        });

        assertEquals(1, e5.getErrorList().size());

    }

    @DisplayName("Create of ConditionModel exception test")
    @Test
    void createConditionModelExceptionTestClass() {
        HashMap<String, Integer> classNameIdMap = new HashMap<>();
        HashMap<String, Integer> propertyNameIdMap = new HashMap<>();
        classNameIdMap.put("TestClassA", 1);
        NameIdPairsDomain nameIdPairsDomain = new NameIdPairsDomain(classNameIdMap, propertyNameIdMap);

        ConditionDomain d1 = new ConditionDomain(
                "class",
                "and",
                null,
                "TestClassA",
                null,
                null
        );

        BadRequestBuilder b1 = new BadRequestBuilder();

        BadRequestException e1 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d1, nameIdPairsDomain, b1);
            throw b1.build();
        });

        assertEquals(1, e1.getErrorList().size());

        ConditionDomain d2 = new ConditionDomain(
                "class",
                null,
                Arrays.asList(
                        new ConditionDomain(
                                "class",
                                null,
                                null,
                                "TestClassA",
                                null,
                                null
                        )
                ),
                "TestClassA",
                null,
                null
        );

        BadRequestBuilder b2 = new BadRequestBuilder();

        BadRequestException e2 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d2, nameIdPairsDomain, b2);
            throw b2.build();
        });

        assertEquals(1, e2.getErrorList().size());

        ConditionDomain d3 = new ConditionDomain(
                "class",
                null,
                null,
                null,
                null,
                null
        );

        BadRequestBuilder b3 = new BadRequestBuilder();

        BadRequestException e3 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d3, nameIdPairsDomain, b3);
            throw b3.build();
        });

        assertEquals(1, e3.getErrorList().size());

        ConditionDomain d4 = new ConditionDomain(
                "class",
                null,
                null,
                "unknown",
                null,
                null
        );

        BadRequestBuilder b4 = new BadRequestBuilder();

        NotFoundException e4 = assertThrows(NotFoundException.class, () -> {
            f.createConditionModel(d4, nameIdPairsDomain, b4);
        });

        ConditionDomain d5 = new ConditionDomain(
                "class",
                null,
                null,
                "TestClassA",
                "some",
                null
        );

        BadRequestBuilder b5 = new BadRequestBuilder();

        BadRequestException e5 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d5, nameIdPairsDomain, b5);
            throw b5.build();
        });

        assertEquals(1, e5.getErrorList().size());

        ConditionDomain d6 = new ConditionDomain(
                "class",
                null,
                null,
                "TestClassA",
                null,
                2
        );

        BadRequestBuilder b6 = new BadRequestBuilder();

        BadRequestException e6 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d6, nameIdPairsDomain, b6);
            throw b6.build();
        });

        assertEquals(1, e6.getErrorList().size());
    }

    @DisplayName("Create of ConditionModel exception test")
    @Test
    void createConditionModelExceptionTestProperty() {
        HashMap<String, Integer> classNameIdMap = new HashMap<>();
        HashMap<String, Integer> propertyNameIdMap = new HashMap<>();
        classNameIdMap.put("TestClassA", 1);
        propertyNameIdMap.put("hasValueA", 13);
        NameIdPairsDomain nameIdPairsDomain = new NameIdPairsDomain(classNameIdMap, propertyNameIdMap);

        ConditionDomain d1 = new ConditionDomain(
                "property",
                "and",
                null,
                "hasValueA",
                "min",
                3
        );

        BadRequestBuilder b1 = new BadRequestBuilder();

        BadRequestException e1 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d1, nameIdPairsDomain, b1);
            throw b1.build();
        });

        assertEquals(1, e1.getErrorList().size());

        ConditionDomain d2 = new ConditionDomain(
                "property",
                null,
                Arrays.asList(
                        new ConditionDomain(
                                "class",
                                null,
                                null,
                                "TestClassA",
                                null,
                                null
                        )
                ),
                "unknown",
                "only",
                null
        );

        BadRequestBuilder b2 = new BadRequestBuilder();

        NotFoundException e2 = assertThrows(NotFoundException.class, () -> {
            f.createConditionModel(d2, nameIdPairsDomain, b2);
            throw b2.build();
        });

        ConditionDomain d3 = new ConditionDomain(
                "property",
                null,
                Arrays.asList(
                        new ConditionDomain(
                                "class",
                                null,
                                null,
                                "TestClassA",
                                null,
                                null
                        )
                ),
                "hasValueA",
                "only",
                4
        );
        BadRequestBuilder b3 = new BadRequestBuilder();

        BadRequestException e3 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d3, nameIdPairsDomain, b3);
            throw b3.build();
        });
    }


    @DisplayName("Create of ConditionModel exception test")
    @Test
    void createConditionModelExceptionTestNot() {
        HashMap<String, Integer> classNameIdMap = new HashMap<>();
        HashMap<String, Integer> propertyNameIdMap = new HashMap<>();
        classNameIdMap.put("TestClassA", 1);
        propertyNameIdMap.put("hasValueA", 13);
        NameIdPairsDomain nameIdPairsDomain = new NameIdPairsDomain(classNameIdMap, propertyNameIdMap);

        ConditionDomain d1 = new ConditionDomain(
                "not",
                "and",
                Arrays.asList(
                        new ConditionDomain(
                                "class",
                                null,
                                null,
                                "TestClassA",
                                null,
                                null
                        )
                ),
                null,
                null,
                null
        );

        BadRequestBuilder b1 = new BadRequestBuilder();

        BadRequestException e1 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d1, nameIdPairsDomain, b1);
            throw b1.build();
        });

        assertEquals(1, e1.getErrorList().size());

        ConditionDomain d2 = new ConditionDomain(
                "not",
                null,
                null,
                "unknown",
                "only",
                null
        );

        BadRequestBuilder b2 = new BadRequestBuilder();

        BadRequestException e2 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d2, nameIdPairsDomain, b2);
            throw b2.build();
        });

        assertEquals(1, e2.getErrorList().size());

        ConditionDomain d3 = new ConditionDomain(
                "not",
                null,
                Arrays.asList(
                        new ConditionDomain(
                                "class",
                                null,
                                null,
                                "TestClassA",
                                null,
                                null
                        )
                ),
                "hasValueA",
                null,
                null
        );
        BadRequestBuilder b3 = new BadRequestBuilder();

        BadRequestException e3 = assertThrows(BadRequestException.class, () -> {
            f.createConditionModel(d3, nameIdPairsDomain, b3);
            throw b3.build();
        });

        assertEquals(1, e3.getErrorList().size());
    }

}
