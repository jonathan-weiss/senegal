package ch.senegal.engine.model

import ch.senegal.engine.plugin.*
import ch.senegal.engine.plugin.resolver.PluginResolver
import ch.senegal.plugin.model.FacetValue
import ch.senegal.plugin.model.ModelNode
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MutableModelNodeTest {

    private val rootConceptName = TestEntityConcept.conceptName
    private val conceptName = TestEntityAttributeConcept.conceptName
    private val purposeName = TestKotlinFieldPurpose.purposeName
    private val stringFacetName = testFieldNameFacet.facetName
    private val intFacetName = testFieldLengthFacet.facetName
    private val enumFacetName = testFieldTypeFacet.facetName

    private val expectedStringValue = "test dummy value"
    private val expectedIntValue = 42
    private val expectedEnumValue = "kotlin.String"


    @Test
    fun `should convert data types of facets properly`() {

        val entityAttributeNode = prepareModelNodeTree()

        assertNotNull(entityAttributeNode)
        assertEquals(expectedStringValue, entityAttributeNode.getStringFacetValue(purposeName, stringFacetName))
        assertEquals(expectedIntValue, entityAttributeNode.getIntFacetValue(purposeName, intFacetName))
        assertEquals(expectedEnumValue, entityAttributeNode.getEnumFacetOption(purposeName, enumFacetName)?.name)
    }

    @Test
    fun `should fail when accessing with the wrong type`() {

        val entityAttributeNode = prepareModelNodeTree()

        assertNotNull(entityAttributeNode)
        assertThrows(IllegalArgumentException::class.java) { -> entityAttributeNode.getIntFacetValue(purposeName, stringFacetName) }
        assertThrows(IllegalArgumentException::class.java) { -> entityAttributeNode.getStringFacetValue(purposeName, intFacetName) }
        assertThrows(IllegalArgumentException::class.java) { -> entityAttributeNode.getEnumFacetOption(purposeName, stringFacetName) }
    }

    private fun prepareModelNodeTree(): ModelNode {
        val plugins = TestPluginFinder.findAllTestPlugins()
        val resolvedPlugins = PluginResolver.resolvePlugins(plugins)
        val modelTree = MutableModelTree(resolvedPlugins)

        val resolvedRootConcept = resolvedPlugins.getConceptByConceptName(rootConceptName)
            ?: fail("Root Concept not found: $rootConceptName")
        val resolvedConcept = resolvedPlugins.getConceptByConceptName(conceptName)
            ?: fail("Concept not found: $conceptName")
        val rootModelNode = modelTree.createAndAddMutableModelNode(resolvedConcept = resolvedRootConcept)
        val modelNode = rootModelNode.createAndAddMutableModelNode(resolvedConcept = resolvedConcept)

        val resolvedStringFacet = resolvedPlugins.getResolvedFacet(purposeName, stringFacetName)
            ?: fail("Facet not found: $purposeName, $stringFacetName")
        modelNode.addFacetValue(resolvedFacet = resolvedStringFacet, FacetValue.of(expectedStringValue))

        val resolvedIntFacet = resolvedPlugins.getResolvedFacet(purposeName, intFacetName)
            ?: fail("Facet not found: $purposeName, $intFacetName")
        modelNode.addFacetValue(resolvedFacet = resolvedIntFacet, FacetValue.of(expectedIntValue))

        val resolvedEnumFacet = resolvedPlugins.getResolvedFacet(purposeName, enumFacetName)
            ?: fail("Facet not found: $purposeName, $enumFacetName")
        modelNode.addFacetValue(resolvedFacet = resolvedEnumFacet, FacetValue.of(expectedEnumValue))

        return modelNode
    }
}
