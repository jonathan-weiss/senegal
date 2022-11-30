package ch.senegal.engine.plugin

import ch.senegal.engine.plugin.tree.PluginTreeCreator
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PluginTreeCreatorTest {

    @Test
    fun createPluginTree() {
        val entity = TestEntityConcept()
        val entityAttribute = TestEntityAttributeConcept()
        val mapper = TestMapperConcept()
        val kotlinModel = TestKotlinModelPurpose()
        val kotlinModelField = TestKotlinFieldPurpose()

        val plugins: Set<Plugin> = setOf(entity, entityAttribute, mapper, kotlinModel, kotlinModelField)
        val pluginTree = PluginTreeCreator.createPluginTree(plugins)


        // assert
        assertNotNull(pluginTree)
        assertEquals(2, pluginTree.rootConceptNodes.size)
        assertEquals(entity, pluginTree.getConceptNodeByName(ConceptName("TestEntity"))?.concept)
        assertEquals(1, pluginTree.getConceptNodeByName(ConceptName("TestEntity"))?.enclosedConcepts?.size)
        assertEquals(1, pluginTree.getConceptNodeByName(ConceptName("TestEntity"))?.enclosedPurposes?.size)
        assertEquals(entityAttribute, pluginTree.getConceptNodeByName(ConceptName("TestEntityAttribute"))?.concept)
    }

    class TestEntityConcept: Concept {
        override val conceptName: ConceptName
            get() = ConceptName("TestEntity")

        override val enclosingConceptName: ConceptName?
            get() = null

    }

    class TestMapperConcept: Concept {
        override val conceptName: ConceptName
            get() = ConceptName("TestMapperConcept")

        override val enclosingConceptName: ConceptName?
            get() = null

    }


    class TestEntityAttributeConcept: Concept {
        override val conceptName: ConceptName
            get() = ConceptName("TestEntityAttribute")
        override val enclosingConceptName: ConceptName
            get() = ConceptName("TestEntity")

    }

    class TestKotlinModelPurpose: Purpose {
        override val purposeName: PurposeName
            get() = PurposeName("TestKotlinModel")
        override val enclosingConceptName: ConceptName
            get() = ConceptName("TestEntity")
        override val purposeDecors: Set<PurposeDecor>
            get() = setOf(TestClassnameDecor(), TestPackageDecor())
    }

    class TestClassnameDecor: PurposeDecor {
        override val purposeDecorName: PurposeDecorName
            get() = PurposeDecorName("Classname")
    }

    class TestPackageDecor: PurposeDecor {
        override val purposeDecorName: PurposeDecorName
            get() = PurposeDecorName("Package")
    }

    class TestKotlinFieldPurpose: Purpose {
        override val purposeName: PurposeName
            get() = PurposeName("TestKotlinField")
        override val enclosingConceptName: ConceptName
            get() = ConceptName("TestEntityAttribute")
        override val purposeDecors: Set<PurposeDecor>
            get() = setOf(TestFieldTypeDecor())
    }

    class TestFieldTypeDecor: PurposeDecor {
        override val purposeDecorName: PurposeDecorName
            get() = PurposeDecorName("FieldType")
    }


}
