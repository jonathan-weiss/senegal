package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedFacet
import ch.senegal.plugin.Concept
import ch.senegal.plugin.FacetName
import ch.senegal.plugin.PurposeFacetCombinedName
import ch.senegal.plugin.PurposeName
import ch.senegal.plugin.model.FacetValue
import ch.senegal.plugin.model.ModelNode

class MutableModelNode(val resolvedConcept: ResolvedConcept,
                       private val parentMutableModelInstance: MutableModelInstance,
): MutableModelInstance(), ModelNode {

    val nodeFacetValues: MutableMap<PurposeFacetCombinedName, FacetValue> = mutableMapOf()

    override fun parentMutableModelInstance(): MutableModelInstance {
        return parentMutableModelInstance
    }

    fun addFacetValue(facet: ResolvedFacet, facetValue: FacetValue) {
        nodeFacetValues[facet.purposeFacetName] = facetValue
    }

    override fun concept(): Concept {
        return resolvedConcept.concept
    }

    override fun parentModelNode(): ModelNode? {
        return when(parentMutableModelInstance) {
            is MutableModelNode -> this
            is MutableModelTree -> null
        }
    }

    override fun getFacetValue(purposeName: PurposeName, facetName: FacetName): FacetValue? {
        val purposeFacetCombinedName = PurposeFacetCombinedName.of(purposeName, facetName)
        return nodeFacetValues[purposeFacetCombinedName]
    }
}
