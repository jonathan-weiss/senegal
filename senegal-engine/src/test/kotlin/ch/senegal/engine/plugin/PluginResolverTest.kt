package ch.senegal.engine.plugin

import ch.senegal.engine.plugin.resolver.PluginResolver
import ch.senegal.plugin.ConceptName
import ch.senegal.plugin.Plugin
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class PluginResolverTest {

    @Test
    fun createPluginTree() {
        val entity = TestEntityConcept
        val entityAttribute = TestEntityAttributeConcept
        val mapper = TestMapperConcept
        val kotlinModel = TestKotlinModelPurpose
        val kotlinModelField = TestKotlinFieldPurpose

        val plugins: Set<Plugin> = setOf(entity, entityAttribute, mapper, kotlinModel, kotlinModelField)
        val resolvedPlugins = PluginResolver.resolvePlugins(plugins)


        // assert
        assertNotNull(resolvedPlugins)
        assertEquals(3, resolvedPlugins.allResolvedConcepts.size)
        assertEquals(2, resolvedPlugins.resolvedRootConcepts.size)
        assertEquals(entity, resolvedPlugins.getConceptByConceptName(ConceptName("TestEntity"))?.concept)
        assertEquals(1, resolvedPlugins.getConceptByConceptName(ConceptName("TestEntity"))?.enclosedConcepts?.size)
        assertEquals(1, resolvedPlugins.getConceptByConceptName(ConceptName("TestEntity"))?.enclosedPurposes?.size)
        assertEquals(entityAttribute, resolvedPlugins.getConceptByConceptName(ConceptName("TestEntityAttribute"))?.concept)
    }
}
