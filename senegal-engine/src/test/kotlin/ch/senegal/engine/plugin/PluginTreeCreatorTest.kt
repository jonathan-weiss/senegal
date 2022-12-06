package ch.senegal.engine.plugin

import ch.senegal.engine.plugin.tree.PluginTreeCreator
import ch.senegal.plugin.ConceptName
import ch.senegal.plugin.Plugin
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PluginTreeCreatorTest {

    @Test
    fun createPluginTree() {
        val entity = TestEntityConcept
        val entityAttribute = TestEntityAttributeConcept
        val mapper = TestMapperConcept
        val kotlinModel = TestKotlinModelPurpose
        val kotlinModelField = TestKotlinFieldPurpose

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
}
