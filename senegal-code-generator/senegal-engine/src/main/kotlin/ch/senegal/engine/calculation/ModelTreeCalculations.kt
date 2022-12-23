package ch.senegal.engine.calculation

import ch.senegal.engine.model.MutableModelNode
import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.model.converter.StringEnumFacetConverter
import ch.senegal.engine.plugin.resolver.ResolvedFacet
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.plugin.*
import ch.senegal.engine.model.FacetValue
import ch.senegal.plugin.model.ModelNode
import java.nio.file.Path

object ModelTreeCalculations {

    fun executeCalculations(mutableModelTree: MutableModelTree) {
        mutableModelTree.getAllModelNodes().forEach { calculateModelNode(it) }
    }

    private fun calculateModelNode(mutableModelNode: MutableModelNode) {
        mutableModelNode.resolvedConcept.enclosedFacets
            .forEach { resolvedFacet -> calculateFacet(resolvedFacet, mutableModelNode) }
    }

    private fun calculateFacet(resolvedFacet: ResolvedFacet, mutableModelNode: MutableModelNode) {
        val currentFacetValue = mutableModelNode.nodeFacetValues[resolvedFacet.purposeFacetName]

        val facet = resolvedFacet.facet
        val newFacetValue = enhanceAndValidate(facet, mutableModelNode, currentFacetValue)

        if (newFacetValue == null) {
            mutableModelNode.nodeFacetValues.remove(resolvedFacet.purposeFacetName)
        } else {
            mutableModelNode.nodeFacetValues[resolvedFacet.purposeFacetName] = newFacetValue
        }
    }

    private fun enhanceAndValidate(facet: Facet, modelNode: ModelNode, currentFacetValue: FacetValue?): FacetValue? {
        return when (facet) {
            is StringEnumerationFacet -> enhanceAndValidateStringEnum(facet, modelNode, currentFacetValue)
            is StringFacet -> enhanceAndValidateString(facet, modelNode, currentFacetValue)
            is BooleanFacet -> enhanceAndValidateBoolean(facet, modelNode, currentFacetValue)
            is IntegerFacet -> enhanceAndValidateInteger(facet, modelNode, currentFacetValue)
            is DirectoryFacet -> enhanceAndValidateDirectory(facet, modelNode, currentFacetValue)
            is FileFacet -> enhanceAndValidateFile(facet, modelNode, currentFacetValue)
            else -> throw IllegalArgumentException("FacetType is not supported: $facet")
        }
    }

    private fun enhanceAndValidateStringEnum(
        facet: StringEnumerationFacet,
        modelNode: ModelNode,
        facetValue: FacetValue?
    ): FacetValue? {
        return facet
            .enhanceFacetValue(modelNode, facetValue?.value as String?)
            ?.also { StringEnumFacetConverter.throwIfNotValidEnumerationValue(facet, it) }
            ?.let { FacetValue.of(it) }

    }

    private fun enhanceAndValidateString(
        facet: StringFacet,
        modelNode: ModelNode,
        facetValue: FacetValue?
    ): FacetValue? {
        return facet
            .enhanceFacetValue(modelNode, facetValue?.value as String?)
            ?.let { FacetValue.of(it) }
    }

    private fun enhanceAndValidateInteger(
        facet: IntegerFacet,
        modelNode: ModelNode,
        facetValue: FacetValue?
    ): FacetValue? {
        return facet
            .enhanceFacetValue(modelNode, facetValue?.value as Int?)
            ?.let { FacetValue.of(it) }
    }

    private fun enhanceAndValidateBoolean(
        facet: BooleanFacet,
        modelNode: ModelNode,
        facetValue: FacetValue?
    ): FacetValue? {
        return facet
            .enhanceFacetValue(modelNode, facetValue?.value as Boolean?)
            ?.let { FacetValue.of(it) }
    }

    private fun enhanceAndValidateFile(
        facet: FileFacet,
        modelNode: ModelNode,
        facetValue: FacetValue?
    ): FacetValue? {
        return facet
            .enhanceFacetValue(modelNode, facetValue?.value as Path?)
            ?.let { FacetValue.of(it) }
    }

    private fun enhanceAndValidateDirectory(
        facet: DirectoryFacet,
        modelNode: ModelNode,
        facetValue: FacetValue?
    ): FacetValue? {
        return facet
            .enhanceFacetValue(modelNode, facetValue?.value as Path?)
            ?.let { FacetValue.of(it) }
    }

}
