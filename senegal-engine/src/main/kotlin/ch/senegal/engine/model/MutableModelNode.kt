package ch.senegal.engine.model

import ch.senegal.engine.plugin.resolver.ResolvedConcept
import ch.senegal.engine.plugin.resolver.ResolvedFacet
import ch.senegal.plugin.model.FacetValue

class MutableModelNode(val resolvedConcept: ResolvedConcept,
                       private val parentMutableModelInstance: MutableModelInstance,
): MutableModelInstance() {

    val nodeFacetValues: MutableMap<ResolvedFacet, FacetValue> = mutableMapOf()

    override fun parentModelInstance(): MutableModelInstance {
        return parentMutableModelInstance
    }

    fun addFacetValue(facet: ResolvedFacet, facetValue: FacetValue) {
        nodeFacetValues[facet] = facetValue
    }

//    fun getFacetValue(facetName: FacetName): FacetValue? {
//        return nodeFacetValues[facetName]
//    }
}
