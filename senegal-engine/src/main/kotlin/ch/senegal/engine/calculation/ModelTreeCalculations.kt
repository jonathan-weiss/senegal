package ch.senegal.engine.calculation

import ch.senegal.engine.model.MutableModelNode
import ch.senegal.engine.model.MutableModelTree
import ch.senegal.engine.plugin.resolver.ResolvedFacet
import ch.senegal.engine.plugin.resolver.ResolvedPlugins
import ch.senegal.plugin.Facet

object ModelTreeCalculations {

    fun executeCalculations(mutableModelTree: MutableModelTree, resolvedPlugins: ResolvedPlugins) {
        mutableModelTree.getAllModelNodes().forEach { calculateModelNode(it) }
    }

    private fun calculateModelNode(mutableModelNode: MutableModelNode) {
        mutableModelNode.resolvedConcept.enclosedFacets
            .forEach { resolvedFacet -> calculateFacet(resolvedFacet, mutableModelNode) }
    }

    private fun calculateFacet(resolvedFacet: ResolvedFacet, mutableModelNode: MutableModelNode) {
        val currentFacetValue = mutableModelNode.nodeFacetValues[resolvedFacet.purposeFacetName]
        val newFacetValue = resolvedFacet.facet.calculateFacetValue(modelNode = mutableModelNode, currentFacetValue)
        if(newFacetValue == null) {
            mutableModelNode.nodeFacetValues.remove(resolvedFacet.purposeFacetName)
        } else {
            mutableModelNode.nodeFacetValues[resolvedFacet.purposeFacetName] = newFacetValue
        }

    }
}
