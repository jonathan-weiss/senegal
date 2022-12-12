package ch.senegal.plugin

import ch.senegal.plugin.model.FacetValue
import ch.senegal.plugin.model.ModelNode

interface Facet {

    val facetName: FacetName

    val enclosingConceptName: ConceptName

    val facetType: FacetType

    val isOnlyCalculated: Boolean
        get() = false

    fun calculateFacetValue(modelNode: ModelNode, facetValue: FacetValue?): FacetValue? {
        return facetValue
    }

}
